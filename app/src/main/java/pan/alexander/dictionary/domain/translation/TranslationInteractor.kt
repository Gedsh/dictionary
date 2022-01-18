package pan.alexander.dictionary.domain.translation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface TranslationInteractor {
    fun getTranslations(wordsFlow: SharedFlow<String>): Flow<TranslationResponseState>
}
