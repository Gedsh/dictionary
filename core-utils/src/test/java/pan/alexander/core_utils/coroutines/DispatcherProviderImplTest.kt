package pan.alexander.core_utils.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DispatcherProviderImplTest {

    private lateinit var dispatcherProvider: DispatcherProviderImpl

    @Before
    fun setUp() {
        dispatcherProvider = DispatcherProviderImpl()
    }

    @Test
    fun dispatcherProviderImpl_DispatcherIo_NotNull() {
        assertNotNull(dispatcherProvider.io())
    }

    @Test
    fun dispatcherProviderImpl_DispatcherIo_CorrectInstance_ReturnsTrue() {
        assertTrue(dispatcherProvider.io() is CoroutineDispatcher)
    }

    @Test
    fun dispatcherProviderImpl_DispatcherUi_NotNull() {
        assertNotNull(dispatcherProvider.ui())
    }

    @Test
    fun dispatcherProviderImpl_DispatcherUi_CorrectInstance_ReturnsTrue() {
        assertTrue(dispatcherProvider.ui() is CoroutineDispatcher)
    }
}
