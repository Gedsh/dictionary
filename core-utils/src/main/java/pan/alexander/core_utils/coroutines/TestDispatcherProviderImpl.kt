package pan.alexander.core_utils.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler

@ExperimentalCoroutinesApi
class TestDispatcherProviderImpl(
    private val scheduler: TestCoroutineScheduler
): DispatcherProvider {

    override fun io() = StandardTestDispatcher(scheduler)

    override fun ui() = StandardTestDispatcher(scheduler)
}
