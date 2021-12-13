package pan.alexander.dictionary.data.local

import pan.alexander.dictionary.database.MeaningDao
import pan.alexander.dictionary.database.SearchResponseDao
import pan.alexander.dictionary.database.TranslationDao
import pan.alexander.dictionary.domain.entities.MeaningEntity
import pan.alexander.dictionary.domain.entities.SearchResponseEntity
import pan.alexander.dictionary.domain.entities.TranslationEntity

class LocalDataSourceImpl(
    private val searchResponseDao: SearchResponseDao,
    private val translationDao: TranslationDao,
    private val meaningDao: MeaningDao
) : LocalDataSource {
    override suspend fun getSearchResponseIdsByWord(word: String): String =
        searchResponseDao.getIdsByWord(word) ?: ""

    override suspend fun addSearchResponse(entity: SearchResponseEntity) =
        searchResponseDao.insert(entity)

    override suspend fun getTranslationsByIds(ids: List<Long>): List<TranslationEntity> =
        translationDao.getByIds(ids)

    override suspend fun addTranslations(entities: List<TranslationEntity>) =
        translationDao.insert(entities)

    override suspend fun getMeaningsByIds(ids: List<Long>): List<MeaningEntity> =
        meaningDao.getByIds(ids)

    override suspend fun addMeanings(entities: List<MeaningEntity>) =
        meaningDao.insert(entities)

}
