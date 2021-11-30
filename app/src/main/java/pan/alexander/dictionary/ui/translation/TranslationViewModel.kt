package pan.alexander.dictionary.ui.translation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pan.alexander.dictionary.domain.TranslationInteractor
import pan.alexander.dictionary.domain.TranslationResponseState
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
        flatMapLatest {
            flow {

                if (it.isBlank()) {
                    return@flow
                }

                viewStateMutableLiveData.value = TranslationViewState.Loading()

                runCatching {
                    interactor.getTranslations(it)
                }.onSuccess {
                    when (it) {
                        is TranslationResponseState.Success ->
                            emit(TranslationViewState.Success(it.translations))
                        is TranslationResponseState.NoConnection ->
                            emit(TranslationViewState.NoConnection)
                    }
                }.onFailure {
                    AppLogger.logE("Requesting translation failed", it)
                    emit(TranslationViewState.Error(it))
                }

            }
        }.also {
            viewModelScope.launch {
                it.collect {
                    viewStateMutableLiveData.value = it
                }
            }
        }
    }

    fun getTranslations(word: String = request.replayCache.firstOrNull() ?: "") {
        request.tryEmit(word)
    }

}
