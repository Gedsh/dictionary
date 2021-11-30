package pan.alexander.dictionary.domain

import pan.alexander.dictionary.domain.entities.Translation

sealed class TranslationResponseState {
    data class Success(val translations: List<Translation>) : TranslationResponseState()
    object NoConnection : TranslationResponseState()
}
