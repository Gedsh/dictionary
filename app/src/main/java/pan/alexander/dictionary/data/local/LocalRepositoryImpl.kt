package pan.alexander.dictionary.data.local

import pan.alexander.core_db.enities.MeaningEntity
import pan.alexander.core_db.enities.SearchResponseEntity
import pan.alexander.core_db.enities.TranslationEntity
import pan.alexander.core_utils.converters.LongListStringConverter
import pan.alexander.dictionary.domain.LocalRepository

class LocalRepositoryImpl(
    private val localDataSource: LocalDataSource
) : LocalRepository {
    override suspend fun getSearchResponseIdsByWord(word: String): List<Long> =
        LongListStringConverter.stringToList(localDataSource.getSearchResponseIdsByWord(word))

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

    override suspend fun getHistory(): List<String> =
        localDataSource.getHistory()

    override suspend fun addWordToHistory(word: String) =
        localDataSource.addWordToHistory(word)

    override suspend fun deleteWordFromHistory(word: String) =
        localDataSource.deleteWordFromHistory(word)

    override suspend fun clearHistory() =
        localDataSource.clearHistory()

}
