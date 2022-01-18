package pan.alexander.dictionary.ui.translation

import pan.alexander.dictionary.domain.dto.TranslationDto

sealed class TranslationViewState {
    data class Success(val translations: List<TranslationDto>) : TranslationViewState()
    data class Error(val error: Throwable) : TranslationViewState()
    data class Loading(val progress: Int? = null) : TranslationViewState()
    object NoConnection : TranslationViewState()
}
