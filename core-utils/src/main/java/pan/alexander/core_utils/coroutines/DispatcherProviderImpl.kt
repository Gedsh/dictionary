package pan.alexander.core_utils.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProviderImpl : DispatcherProvider {

    override fun io(): CoroutineDispatcher = Dispatchers.IO

    override fun ui(): CoroutineDispatcher = Dispatchers.Main
}
