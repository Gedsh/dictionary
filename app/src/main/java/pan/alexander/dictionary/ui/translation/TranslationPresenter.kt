package pan.alexander.dictionary.ui.translation

import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import pan.alexander.dictionary.domain.TranslationInteractor
import pan.alexander.dictionary.utils.logger.AppLogger
import javax.inject.Inject

class TranslationPresenter @Inject constructor(
    private val interactor: TranslationInteractor
) : TranslationContract.Presenter() {

    @Suppress("USELESS_CAST")
    override val request: BehaviorSubject<String> = BehaviorSubject.create<String>().apply {
        switchMapSingle { word ->
            currentView?.setState(TranslationContract.ViewState.Loading())
            interactor.getTranslations(word).map {
                TranslationContract.ViewState.Success(it) as TranslationContract.ViewState
            }.onErrorReturn {
                AppLogger.logE("Requesting translation failed", it)
                TranslationContract.ViewState.Error(it)
            }
        }.subscribeBy(
            onNext = {
                currentView?.setState(it)
            }
        ).autoDispose()
    }

    override fun getTranslations(word: String) {
        if (word.isNotBlank()) {
            request.onNext(word)
        }
    }

}
