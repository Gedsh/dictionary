package pan.alexander.dictionary.ui.history

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pan.alexander.core_ui.base.BaseViewModel
import pan.alexander.core_utils.logger.AppLogger
import pan.alexander.dictionary.domain.history.HistoryInteractor

class HistoryViewModel(
    private val interactor: HistoryInteractor
) : BaseViewModel<List<String>>() {

    fun getHistory() {
        viewModelScope.launch {
            try {
                viewStateMutableLiveData.value = interactor.getHistory()
            } catch (e: Exception) {
                AppLogger.logE("Failed to retrieve history", e)
            }
        }
    }

    fun deleteWordFromHistory(word: String) {
        viewModelScope.launch {
            try {
                interactor.deleteWordFromHistory(word)
                viewStateMutableLiveData.value = interactor.getHistory()
            } catch (e: Exception) {
                AppLogger.logE("Failed to delete word $word", e)
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            try {
                interactor.clearHistory()
                viewStateMutableLiveData.value = emptyList()
            } catch (e: java.lang.Exception) {
                AppLogger.logE("Failed to clear history", e)
            }
        }
    }

}
