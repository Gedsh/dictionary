package pan.alexander.dictionary.ui.translation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito.*
import pan.alexander.dictionary.BuildConfig
import pan.alexander.dictionary.di.ACTIVITY_RETAINED_SCOPE
import pan.alexander.dictionary.di.AppModules
import pan.alexander.dictionary.domain.dto.TranslationDto
import pan.alexander.dictionary.domain.translation.TranslationInteractor
import pan.alexander.dictionary.domain.translation.TranslationResponseState
import java.io.IOException
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@Suppress("BlockingMethodInNonBlockingContext")
class TranslationViewModelTest : KoinTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var scope: Scope

    private val word = "word"

    private val translationDto = TranslationDto(
        0,
        word,
        emptyList()
    )

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
        modules(AppModules.vmModule)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mock(clazz.java)
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        scope = KoinJavaComponent.getKoin()
            .createScope(ACTIVITY_RETAINED_SCOPE, named(ACTIVITY_RETAINED_SCOPE))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        scope.close()
    }

    @Test
    fun getTranslations_EmptyText() = runTest {

        val viewModel = get<TranslationViewModel>()

        val interactor = scope.declareMock<TranslationInteractor>()

        viewModel.getTranslations("")

        verify(interactor, never()).getTranslations(org.mockito.kotlin.any())

        assertTrue(viewModel.getViewStateLiveData().value == null)
    }

    @Test
    fun getTranslations_ImproperText() = runTest {

        val viewModel = get<TranslationViewModel>()

        val interactor = scope.declareMock<TranslationInteractor>()

        viewModel.getTranslations("word$")

        verify(interactor, never()).getTranslations(org.mockito.kotlin.any())

        assertTrue(viewModel.getViewStateLiveData().value == null)
    }

    @Test
    fun getTranslations_Error() = runTest {

        val viewModel = get<TranslationViewModel>()

        val interactor = scope.declareMock<TranslationInteractor>()
        `when`(interactor.getTranslations(org.mockito.kotlin.any()))
            .thenThrow(IOException::class.java)

        viewModel.getTranslations(word)

        verify(interactor).getTranslations(org.mockito.kotlin.any())

        assertTrue(viewModel.getViewStateLiveData().value is TranslationViewState.Error)
    }

    @Test
    fun getTranslations_NoConnection() = runTest {

        val viewModel = get<TranslationViewModel>()

        val interactor = scope.declareMock<TranslationInteractor>()
        `when`(interactor.getTranslations(org.mockito.kotlin.any()))
            .thenReturn(
                flowOf(TranslationResponseState.NoConnection)
            )

        viewModel.getTranslations(word)

        verify(interactor).getTranslations(org.mockito.kotlin.any())

        assertEquals(
            TranslationViewState.NoConnection,
            viewModel.getViewStateLiveData().value
        )
    }

    @Test
    fun getTranslations_Success() = runTest {

        val viewModel = get<TranslationViewModel>()

        val interactor = scope.declareMock<TranslationInteractor>()
        `when`(interactor.getTranslations(org.mockito.kotlin.any()))
            .thenReturn(
                flowOf(TranslationResponseState.Success(listOf(translationDto)))
            )

        viewModel.getTranslations(word)

        verify(interactor).getTranslations(org.mockito.kotlin.any())

        assertEquals(
            TranslationViewState.Success(listOf(translationDto)),
            viewModel.getViewStateLiveData().value
        )
    }


    @Test
    fun getLastWord_NoWords() {

        val viewModel = get<TranslationViewModel>()

        assertEquals(viewModel.getLastWord(), "")
    }

    @Test
    fun getLastWord_SeveralWords() {

        val viewModel = get<TranslationViewModel>()

        val interactor = scope.declareMock<TranslationInteractor>()
        `when`(interactor.getTranslations(org.mockito.kotlin.any()))
            .thenReturn(
                flowOf(TranslationResponseState.Success(listOf(translationDto)))
            )

        viewModel.getTranslations("some other word")
        viewModel.getTranslations(word)

        assertEquals(viewModel.getLastWord(), word)
    }
}
