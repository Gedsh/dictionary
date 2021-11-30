package pan.alexander.dictionary.domain

import pan.alexander.dictionary.domain.entities.Translation

interface RemoteRepository {
    suspend fun requestTranslations(word: String): List<Translation>
}
