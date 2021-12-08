package pan.alexander.dictionary.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface TranslationInteractor {
    fun getTranslations(flow: SharedFlow<String>): Flow<TranslationResponseState>
}
