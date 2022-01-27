package pan.alexander.dictionary.base

import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations

open class BaseMockitoTest {

    private lateinit var closeable: AutoCloseable

    @Before
    fun openMocks() {
        closeable = MockitoAnnotations.openMocks(this)
    }

    @After
    fun releaseMocks() {
        closeable.close()
    }
}
