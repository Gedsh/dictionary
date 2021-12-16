package pan.alexander.dictionary.domain

import pan.alexander.core_db.enities.MeaningEntity
import pan.alexander.core_db.enities.SearchResponseEntity
import pan.alexander.core_db.enities.TranslationEntity

interface LocalRepository {
    suspend fun getSearchResponseIdsByWord(word: String): List<Long>
    suspend fun addSearchResponse(entity: SearchResponseEntity)

    suspend fun getTranslationsByIds(ids: List<Long>): List<TranslationEntity>
    suspend fun addTranslations(entities: List<TranslationEntity>)

    suspend fun getMeaningByIds(ids: List<Long>): List<MeaningEntity>
    suspend fun addMeanings(entities: List<MeaningEntity>)
}
