package pan.alexander.dictionary.data.local

import pan.alexander.core_db.enities.MeaningEntity
import pan.alexander.core_db.enities.SearchResponseEntity
import pan.alexander.core_db.enities.TranslationEntity
import pan.alexander.core_db.database.LIST_ITEM_SEPARATOR
import pan.alexander.core_utils.Constants.NUMBER_REGEX
import pan.alexander.dictionary.domain.LocalRepository

class LocalRepositoryImpl(
    private val localDataSource: LocalDataSource
) : LocalRepository {
    override suspend fun getSearchResponseIdsByWord(word: String): List<Long> =
        localDataSource.getSearchResponseIdsByWord(word)
            .split(LIST_ITEM_SEPARATOR)
            .filter { it.matches(NUMBER_REGEX) }
            .map { it.toLong() }

    override suspend fun addSearchResponse(entity: SearchResponseEntity) =
        localDataSource.addSearchResponse(entity)

    override suspend fun getTranslationsByIds(ids: List<Long>): List<TranslationEntity> =
        localDataSource.getTranslationsByIds(ids)

    override suspend fun addTranslations(entities: List<TranslationEntity>) =
        localDataSource.addTranslations(entities)

    override suspend fun getMeaningByIds(ids: List<Long>): List<MeaningEntity> =
        localDataSource.getMeaningsByIds(ids)

    override suspend fun addMeanings(entities: List<MeaningEntity>) =
        localDataSource.addMeanings(entities)

}
