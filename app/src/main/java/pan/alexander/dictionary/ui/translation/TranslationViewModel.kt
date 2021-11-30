package pan.alexander.dictionary.ui.translation

import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import pan.alexander.dictionary.domain.TranslationInteractor
import pan.alexander.dictionary.domain.TranslationResponseState
import pan.alexander.dictionary.ui.base.BaseViewModel
import pan.alexander.dictionary.utils.logger.AppLogger
import javax.inject.Inject

class TranslationViewModel @Inject constructor(
    private val interactor: TranslationInteractor
) : BaseViewModel<TranslationViewState>() {

    @Suppress("USELESS_CAST")
    private val request: BehaviorSubject<String> = BehaviorSubject.create<String>().apply {
        switchMapSingle { word ->
            viewStateMutableLiveData.value = TranslationViewState.Loading()
            interactor.getTranslations(word).map {
                when (it) {
                    is TranslationResponseState.Success ->
                        TranslationViewState.Success(it.translations) as TranslationViewState
                    is TranslationResponseState.NoConnection ->
                        TranslationViewState.NoConnection
                }
            }.onErrorReturn {
                AppLogger.logE("Requesting translation failed", it)
                TranslationViewState.Error(it)
            }
        }.subscribeBy(
            onNext = {
                viewStateMutableLiveData.value = it
            }
        ).autoDispose()
    }

    fun getTranslations(word: String = request.value ?: "") {
        if (word.isNotBlank()) {
            request.onNext(word)
        }
    }

}
