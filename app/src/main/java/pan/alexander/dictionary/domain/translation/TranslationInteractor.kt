package pan.alexander.dictionary.domain.translation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import java.io.IOException
import kotlin.jvm.Throws

interface TranslationInteractor {
    @Throws(IOException::class)
    fun getTranslations(wordsFlow: SharedFlow<String>): Flow<TranslationResponseState>
}
