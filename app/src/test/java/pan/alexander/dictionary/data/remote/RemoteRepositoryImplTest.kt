package pan.alexander.dictionary.data.remote

import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import org.mockito.Mock
import org.mockito.Mockito.*
import pan.alexander.core_web.pojo.MeaningsPojo
import pan.alexander.core_web.pojo.SearchResponsePojo
import pan.alexander.dictionary.base.BaseMockitoTest
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
@Suppress("BlockingMethodInNonBlockingContext", "UNCHECKED_CAST")
class RemoteRepositoryImplTest : BaseMockitoTest() {

    private lateinit var remoteRepositoryImpl: RemoteRepositoryImpl

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var retrofit: Retrofit

    @Before
    fun setUp() {
        remoteRepositoryImpl = RemoteRepositoryImpl(remoteDataSource, retrofit)
    }

    @Test
    fun requestTranslations_Failed() = runTest {

        `when`(remoteDataSource.requestTranslations(anyString())).thenThrow(IOException::class.java)

        assertThrows(IOException::class.java) {
            runBlocking { remoteRepositoryImpl.requestTranslations(anyString()) }
        }
    }

    @Test
    fun requestTranslations_ResponseUnsuccessful() = runTest {
        val response = mock(Response::class.java) as Response<List<SearchResponsePojo>>

        `when`(response.isSuccessful).thenReturn(false)
        `when`(remoteDataSource.requestTranslations(anyString())).thenReturn(response)

        assertThrows(IOException::class.java) {
            runBlocking { remoteRepositoryImpl.requestTranslations(anyString()) }
        }
    }

    @Test
    fun requestTranslations_ResponseIsEmpty() = runTest {
        val response = mock(Response::class.java) as Response<List<SearchResponsePojo>>

        `when`(response.isSuccessful).thenReturn(true)
        `when`(remoteDataSource.requestTranslations(anyString())).thenReturn(response)

        `when`(response.body()).thenReturn(listOf())

        assertTrue(remoteRepositoryImpl.requestTranslations(anyString()).isEmpty())
    }

    @Test
    fun requestTranslations_ResponseContainsNullId() = runTest {
        val response = mock(Response::class.java) as Response<List<SearchResponsePojo>>

        `when`(response.isSuccessful).thenReturn(true)
        `when`(remoteDataSource.requestTranslations(anyString())).thenReturn(response)

        val meaningsPojo = mock(MeaningsPojo::class.java)
        val searchResponsePojo = SearchResponsePojo(null, "Text", listOf(meaningsPojo))

        `when`(response.body()).thenReturn(listOf(searchResponsePojo))

        assertTrue(remoteRepositoryImpl.requestTranslations(anyString()).isEmpty())
    }

    @Test
    fun requestTranslations_ResponseIsNotEmpty() = runTest {
        val response = mock(Response::class.java) as Response<List<SearchResponsePojo>>

        `when`(response.isSuccessful).thenReturn(true)
        `when`(remoteDataSource.requestTranslations(anyString())).thenReturn(response)

        val meaningsPojo = mock(MeaningsPojo::class.java)
        val searchResponsePojo = SearchResponsePojo(0, "Text", listOf(meaningsPojo))

        `when`(response.body()).thenReturn(listOf(searchResponsePojo))

        assertTrue(remoteRepositoryImpl.requestTranslations(anyString()).isNotEmpty())
    }
}
