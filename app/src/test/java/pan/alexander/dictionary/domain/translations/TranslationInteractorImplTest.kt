package pan.alexander.dictionary.domain.translations

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import pan.alexander.core_db.enities.SearchResponseEntity
import pan.alexander.core_db.enities.TranslationEntity
import pan.alexander.core_utils.coroutines.DispatcherProvider
import pan.alexander.core_utils.coroutines.TestDispatcherProviderImpl
import pan.alexander.dictionary.base.BaseMockitoTest
import pan.alexander.dictionary.domain.LocalRepository
import pan.alexander.dictionary.domain.NetworkRepository
import pan.alexander.dictionary.domain.RemoteRepository
import pan.alexander.dictionary.domain.dto.TranslationDto
import pan.alexander.dictionary.domain.translation.TranslationInteractorImpl
import pan.alexander.dictionary.domain.translation.TranslationResponseState
import pan.alexander.dictionary.tests.WORD
import java.io.IOException
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@Suppress("BlockingMethodInNonBlockingContext")
class TranslationInteractorImplTest : BaseMockitoTest() {

    private lateinit var translationInteractor: TranslationInteractorImpl

    @Mock
    private lateinit var localRepository: LocalRepository

    @Mock
    private lateinit var remoteRepository: RemoteRepository

    @Mock
    private lateinit var networkRepository: NetworkRepository

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcherProvider: DispatcherProvider = TestDispatcherProviderImpl(testScheduler)

    @Before
    fun setUp() {
        translationInteractor = TranslationInteractorImpl(
            localRepository,
            remoteRepository,
            networkRepository,
            testDispatcherProvider
        )
    }

    @Test
    fun getTranslations_NoLocalCache_NoConnection() = runTest {

        `when`(localRepository.getSearchResponseIdsByWord(WORD)).thenReturn(emptyList())
        `when`(networkRepository.isConnectionAvailable()).thenReturn(false)

        assertEquals(
            TranslationResponseState.NoConnection,
            translationInteractor.getTranslations(getWordsFlow()).first()
        )
    }

    @Test
    fun getTranslations_LocalCacheAvailable_NoConnection() = runTest(testScheduler) {

        val translationIds = listOf(10L)
        val translationEntity = TranslationEntity(
            0,
            WORD,
            emptyList()
        )

        `when`(localRepository.getSearchResponseIdsByWord(WORD)).thenReturn(translationIds)
        `when`(localRepository.getTranslationsByIds(translationIds)).thenReturn(listOf(translationEntity))
        `when`(localRepository.getMeaningByIds(translationEntity.meaningIds)).thenReturn(emptyList())

        assertTrue(
            translationInteractor.getTranslations(getWordsFlow()).first() is TranslationResponseState.Success
        )

        verify(localRepository).addWordToHistory(WORD)
    }

    @Test
    fun getTranslations_NoLocalCache_ConnectionAvailable_ResponseWithException() = runTest(testScheduler) {

        `when`(networkRepository.isConnectionAvailable()).thenReturn(true)
        `when`(localRepository.getSearchResponseIdsByWord(WORD)).thenReturn(emptyList())
        `when`(remoteRepository.requestTranslations(WORD)).thenThrow(RuntimeException::class.java)

        assertThrows(RuntimeException::class.java) {
            runBlocking(testScheduler) { translationInteractor.getTranslations(getWordsFlow()).first() }
        }

        verify(remoteRepository).requestTranslations(WORD)
        verify(localRepository, never()).addWordToHistory(WORD)
    }

    @Test
    fun getTranslations_NoLocalCache_ConnectionAvailable_ResponseWithIOException() = runTest(testScheduler) {

        val errorRetryCount = 3

        `when`(networkRepository.isConnectionAvailable()).thenReturn(true)
        `when`(localRepository.getSearchResponseIdsByWord(WORD)).thenReturn(emptyList())
        `when`(remoteRepository.requestTranslations(WORD)).thenThrow(IOException::class.java)

        assertThrows(IOException::class.java) {
            runBlocking(testScheduler) { translationInteractor.getTranslations(getWordsFlow()).first() }
        }

        verify(remoteRepository, times(1 + errorRetryCount)).requestTranslations(WORD)
        verify(localRepository, never()).addWordToHistory(WORD)
    }

    @Test
    fun getTranslations_NoLocalCache_ConnectionAvailable_emptyResponse() = runTest(testScheduler) {

        `when`(networkRepository.isConnectionAvailable()).thenReturn(true)
        `when`(localRepository.getSearchResponseIdsByWord(WORD)).thenReturn(emptyList())
        `when`(remoteRepository.requestTranslations(WORD)).thenReturn(emptyList())

        assertEquals(
            TranslationResponseState.Success(emptyList()),
            translationInteractor.getTranslations(getWordsFlow()).first()
        )

        verify(localRepository, never()).addWordToHistory(WORD)
    }

    @Test
    fun getTranslations_NoLocalCache_ConnectionAvailable_successResponse() = runTest(testScheduler) {

        `when`(networkRepository.isConnectionAvailable()).thenReturn(true)
        `when`(localRepository.getSearchResponseIdsByWord(WORD)).thenReturn(emptyList())

        val translationDtoList = listOf(TranslationDto(0, WORD, emptyList()))
        `when`(remoteRepository.requestTranslations(WORD)).thenReturn(translationDtoList)

        assertEquals(
            TranslationResponseState.Success(translationDtoList),
            translationInteractor.getTranslations(getWordsFlow()).first()
        )

        verify(localRepository).addSearchResponse(SearchResponseEntity(WORD, translationDtoList.map { it.id }))
        verify(localRepository).addTranslations(anyList())
        verify(localRepository).addMeanings(anyList())
        verify(localRepository).addWordToHistory(WORD)
    }

    private fun getWordsFlow() = MutableSharedFlow<String>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).apply {
        tryEmit(WORD)
    }
}
