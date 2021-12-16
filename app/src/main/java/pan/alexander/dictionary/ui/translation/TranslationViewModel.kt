package pan.alexander.dictionary.ui.translation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pan.alexander.dictionary.domain.translation.TranslationInteractor
import pan.alexander.dictionary.domain.translation.TranslationResponseState
import pan.alexander.dictionary.ui.base.BaseViewModel
import pan.alexander.dictionary.utils.logger.AppLogger

@ExperimentalCoroutinesApi
class TranslationViewModel(
    private val interactor: TranslationInteractor
) : BaseViewModel<TranslationViewState>() {

    private val request = MutableSharedFlow<String>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).apply {
        filter {
            it.isNotBlank()
        }.flatMapLatest {
            viewStateMutableLiveData.value = TranslationViewState.Loading()
            handleTranslations(this)
        }.also {
            viewModelScope.launch {
                it.onEach {
                    viewStateMutableLiveData.value = it
                }.collect()
            }
        }
    }

    private fun handleTranslations(flow: SharedFlow<String>) =
        interactor.getTranslations(flow).map { response ->
            when (response) {
                is TranslationResponseState.Success ->
                    TranslationViewState.Success(response.translations)
                is TranslationResponseState.NoConnection ->
                    TranslationViewState.NoConnection
            }
        }.catch { error ->
            AppLogger.logE("Requesting translation failed", error)
            emit(TranslationViewState.Error(error))
        }

    fun getTranslations(word: String = request.replayCache.firstOrNull() ?: "") {
        request.tryEmit(word)
    }

}
