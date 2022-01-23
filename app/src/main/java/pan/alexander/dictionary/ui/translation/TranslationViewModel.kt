package pan.alexander.dictionary.ui.translation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin
import pan.alexander.dictionary.domain.translation.TranslationInteractor
import pan.alexander.dictionary.domain.translation.TranslationResponseState
import pan.alexander.core_ui.base.BaseViewModel
import pan.alexander.core_utils.Constants.WORD_REGEX
import pan.alexander.core_utils.logger.AppLogger.loge
import pan.alexander.dictionary.di.ACTIVITY_RETAINED_SCOPE
import java.lang.Exception

@ExperimentalCoroutinesApi
class TranslationViewModel : BaseViewModel<TranslationViewState>() {

    private val scope = getKoin()
        .getOrCreateScope(ACTIVITY_RETAINED_SCOPE, named(ACTIVITY_RETAINED_SCOPE))
    private val interactor: TranslationInteractor by scope.inject()

    private val request = MutableSharedFlow<String>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).apply {
        filter {
            it.matches(WORD_REGEX)
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
        try {
            interactor.getTranslations(flow).map { response ->
                when (response) {
                    is TranslationResponseState.Success ->
                        TranslationViewState.Success(response.translations)
                    is TranslationResponseState.NoConnection ->
                        TranslationViewState.NoConnection
                }
            }
        } catch(e: Exception) {
            loge("Requesting translation failed", e)
            flowOf(TranslationViewState.Error(e))
        }


    fun getTranslations(word: String = getLastWord()) {
        request.tryEmit(word)
    }

    fun getLastWord() = request.replayCache.firstOrNull() ?: ""

}
