package pan.alexander.dictionary.domain

import pan.alexander.dictionary.domain.entities.MeaningEntity
import pan.alexander.dictionary.domain.entities.SearchResponseEntity
import pan.alexander.dictionary.domain.entities.TranslationEntity

interface LocalRepository {
    suspend fun getSearchResponseIdsByWord(word: String): List<Long>
    suspend fun addSearchResponse(entity: SearchResponseEntity)

    suspend fun getTranslationsByIds(ids: List<Long>): List<TranslationEntity>
    suspend fun addTranslations(entities: List<TranslationEntity>)

    suspend fun getMeaningByIds(ids: List<Long>): List<MeaningEntity>
    suspend fun addMeanings(entities: List<MeaningEntity>)
}
