package pan.alexander.dictionary.ui.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.component.get
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito.*
import pan.alexander.dictionary.BuildConfig
import pan.alexander.dictionary.di.ACTIVITY_RETAINED_SCOPE
import pan.alexander.dictionary.di.AppModules.testModule
import pan.alexander.dictionary.di.AppModules.vmModule
import pan.alexander.dictionary.domain.history.HistoryInteractor
import pan.alexander.dictionary.tests.WORD
import java.io.IOException

@ExperimentalCoroutinesApi
@Suppress("BlockingMethodInNonBlockingContext")
class HistoryViewModelTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var scope: Scope

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
        modules(vmModule, testModule)
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
    fun getHistory_Failed() = runTest {

        val interactor = scope.declareMock<HistoryInteractor>()
        `when`(interactor.getHistory()).thenThrow(IOException::class.java)

        val viewModel = get<HistoryViewModel>()

        viewModel.getHistory()

        delay(1)

        assertEquals(null, viewModel.getViewStateLiveData().value)

        verify(interactor).getHistory()
    }

    @Test
    fun getHistory_Success() = runTest {

        val interactor = scope.declareMock<HistoryInteractor>()
        `when`(interactor.getHistory()).thenReturn(listOf(WORD))

        val viewModel = get<HistoryViewModel>()

        viewModel.getHistory()

        delay(1)

        verify(interactor).getHistory()

        assertEquals(listOf(WORD), viewModel.getViewStateLiveData().value)
    }

    @Test
    fun deleteWordFromHistory_Failed() = runTest {

        val interactor = scope.declareMock<HistoryInteractor>()
        `when`(interactor.deleteWordFromHistory(WORD)).thenThrow(IOException::class.java)

        val viewModel = get<HistoryViewModel>()

        viewModel.deleteWordFromHistory(WORD)

        delay(1)

        assertEquals(null, viewModel.getViewStateLiveData().value)

        verify(interactor).deleteWordFromHistory(WORD)
        verify(interactor, never()).getHistory()
    }

    @Test
    fun deleteWordFromHistory_Success() = runTest {

        val interactor = scope.declareMock<HistoryInteractor>()
        `when`(interactor.getHistory()).thenReturn(listOf(WORD))

        val viewModel = get<HistoryViewModel>()

        viewModel.deleteWordFromHistory(WORD)

        delay(1)

        assertEquals(listOf(WORD), viewModel.getViewStateLiveData().value)

        verify(interactor).deleteWordFromHistory(WORD)
        verify(interactor).getHistory()
    }

    @Test
    fun clearHistory_Failed() = runTest {

        val interactor = scope.declareMock<HistoryInteractor>()
        `when`(interactor.clearHistory()).thenThrow(IOException::class.java)

        val viewModel = get<HistoryViewModel>()

        viewModel.clearHistory()

        delay(1)

        assertEquals(null, viewModel.getViewStateLiveData().value)

        verify(interactor).clearHistory()
    }

    @Test
    fun clearHistory_Success() = runTest {

        val interactor = scope.declareMock<HistoryInteractor>()

        val viewModel = get<HistoryViewModel>()

        viewModel.clearHistory()

        delay(1)

        assertEquals(emptyList<String>(), viewModel.getViewStateLiveData().value)

        verify(interactor).clearHistory()
    }
}
