package pan.alexander.dictionary.data.local

import pan.alexander.core_db.database.HistoryDao
import pan.alexander.core_db.enities.MeaningEntity
import pan.alexander.core_db.enities.SearchResponseEntity
import pan.alexander.core_db.enities.TranslationEntity
import pan.alexander.core_db.database.MeaningDao
import pan.alexander.core_db.database.SearchResponseDao
import pan.alexander.core_db.database.TranslationDao
import pan.alexander.core_db.enities.HistoryEntity

class LocalDataSourceImpl(
    private val searchResponseDao: SearchResponseDao,
    private val translationDao: TranslationDao,
    private val meaningDao: MeaningDao,
    private val historyDao: HistoryDao
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

    override suspend fun getHistory(): List<String> =
        historyDao.getHistoryWords()


    override suspend fun addWordToHistory(word: String) =
        historyDao.insertHistoryRecord(word)

    override suspend fun deleteWordFromHistory(word: String) =
        historyDao.deleteWord(word)

    override suspend fun clearHistory() =
        historyDao.deleteAllWords()

}
