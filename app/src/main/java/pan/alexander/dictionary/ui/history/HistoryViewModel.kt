package pan.alexander.dictionary.ui.history

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin
import pan.alexander.core_ui.base.BaseViewModel
import pan.alexander.core_utils.logger.AppLogger.loge
import pan.alexander.dictionary.di.ACTIVITY_RETAINED_SCOPE
import pan.alexander.dictionary.domain.history.HistoryInteractor

class HistoryViewModel : BaseViewModel<List<String>>() {

    private val scope = getKoin()
        .getOrCreateScope(ACTIVITY_RETAINED_SCOPE, named(ACTIVITY_RETAINED_SCOPE))
    private val interactor: HistoryInteractor by scope.inject()

    fun getHistory() {
        viewModelScope.launch {
            try {
                viewStateMutableLiveData.value = interactor.getHistory()
            } catch (e: Exception) {
                loge("Failed to retrieve history", e)
            }
        }
    }

    fun deleteWordFromHistory(word: String) {
        viewModelScope.launch {
            try {
                interactor.deleteWordFromHistory(word)
                viewStateMutableLiveData.value = interactor.getHistory()
            } catch (e: Exception) {
                loge("Failed to delete word $word", e)
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            try {
                interactor.clearHistory()
                viewStateMutableLiveData.value = emptyList()
            } catch (e: java.lang.Exception) {
                loge("Failed to clear history", e)
            }
        }
    }

}
