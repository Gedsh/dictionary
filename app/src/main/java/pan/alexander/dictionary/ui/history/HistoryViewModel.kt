package pan.alexander.dictionary.ui.history

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import pan.alexander.core_ui.base.BaseViewModel
import pan.alexander.core_utils.logger.AppLogger.loge
import pan.alexander.dictionary.di.ACTIVITY_RETAINED_SCOPE
import pan.alexander.dictionary.domain.history.HistoryInteractor
import pan.alexander.dictionary.test.EspressoIdlingResource

@Suppress("BlockingMethodInNonBlockingContext")
class HistoryViewModel : BaseViewModel<List<String>>(), KoinComponent {

    private val scope = getKoin()
        .getOrCreateScope(ACTIVITY_RETAINED_SCOPE, named(ACTIVITY_RETAINED_SCOPE))
    private val interactor: HistoryInteractor by scope.inject()

    private val espressoIdlingResource: EspressoIdlingResource by inject()

    fun getHistory() {
        viewModelScope.launch {
            try {
                espressoIdlingResource.setIsIdle(false)
                viewStateMutableLiveData.value = interactor.getHistory()
                espressoIdlingResource.setIsIdle(true)
            } catch (e: Exception) {
                loge("Failed to retrieve history", e)
            }
        }
    }

    fun deleteWordFromHistory(word: String) {
        viewModelScope.launch {
            try {
                espressoIdlingResource.setIsIdle(false)
                interactor.deleteWordFromHistory(word)
                viewStateMutableLiveData.value = interactor.getHistory()
                espressoIdlingResource.setIsIdle(true)
            } catch (e: Exception) {
                loge("Failed to delete word $word", e)
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            try {
                espressoIdlingResource.setIsIdle(false)
                interactor.clearHistory()
                viewStateMutableLiveData.value = emptyList()
                espressoIdlingResource.setIsIdle(true)
            } catch (e: java.lang.Exception) {
                loge("Failed to clear history", e)
            }
        }
    }

}
