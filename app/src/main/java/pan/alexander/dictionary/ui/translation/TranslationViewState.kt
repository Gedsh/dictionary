package pan.alexander.dictionary.ui.translation

import pan.alexander.dictionary.domain.entities.Translation

sealed class TranslationViewState {
    data class Success(val translations: List<Translation>) : TranslationViewState()
    data class Error(val error: Throwable) : TranslationViewState()
    data class Loading(val progress: Int? = null) : TranslationViewState()
    object NoConnection : TranslationViewState()
}
