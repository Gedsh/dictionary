package pan.alexander.dictionary.ui.translation

import io.reactivex.rxjava3.subjects.BehaviorSubject
import pan.alexander.dictionary.domain.entities.Translation
import pan.alexander.dictionary.ui.base.BasePresenter

class TranslationContract {

    sealed class ViewState {
        data class Success(val translations: List<Translation>) : ViewState()
        data class Error(val error: Throwable) : ViewState()
        data class Loading(val progress: Int? = null) : ViewState()
    }

    abstract class Presenter : BasePresenter<ViewState>() {
        abstract val request: BehaviorSubject<String>
        abstract fun getTranslations(word: String = request.value ?: "")
    }
}
