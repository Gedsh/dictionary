package pan.alexander.dictionary.data.local

import pan.alexander.dictionary.domain.entities.MeaningEntity
import pan.alexander.dictionary.domain.entities.SearchResponseEntity
import pan.alexander.dictionary.domain.entities.TranslationEntity

interface LocalDataSource {
    suspend fun getSearchResponseIdsByWord(word: String): String
    suspend fun addSearchResponse(entity: SearchResponseEntity)

    suspend fun getTranslationsByIds(ids: List<Long>): List<TranslationEntity>
    suspend fun addTranslations(entities: List<TranslationEntity>)

    suspend fun getMeaningsByIds(ids: List<Long>): List<MeaningEntity>
    suspend fun addMeanings(entities: List<MeaningEntity>)
}
