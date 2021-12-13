package pan.alexander.dictionary.domain

import pan.alexander.dictionary.domain.dto.TranslationDto

interface RemoteRepository {
    suspend fun requestTranslations(word: String): List<TranslationDto>
}
