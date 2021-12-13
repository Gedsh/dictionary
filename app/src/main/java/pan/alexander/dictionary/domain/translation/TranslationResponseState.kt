package pan.alexander.dictionary.domain.translation

import pan.alexander.dictionary.domain.dto.TranslationDto

sealed class TranslationResponseState {
    data class Success(val translations: List<TranslationDto>) : TranslationResponseState()
    object NoConnection : TranslationResponseState()
}
