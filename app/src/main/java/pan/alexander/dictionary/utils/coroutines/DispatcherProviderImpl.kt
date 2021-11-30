package pan.alexander.dictionary.utils.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

class DispatcherProviderImpl : DispatcherProvider {

    override fun io(): CoroutineDispatcher = Dispatchers.IO

    override fun ui(): MainCoroutineDispatcher = Dispatchers.Main
}
