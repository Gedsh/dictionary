package pan.alexander.dictionary.domain

interface TranslationInteractor {
    suspend fun getTranslations(word: String): TranslationResponseState
}
