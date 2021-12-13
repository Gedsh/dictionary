package pan.alexander.dictionary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pan.alexander.dictionary.domain.entities.TranslationEntity

@Dao
interface TranslationDao {

    @Query("SELECT * FROM TranslationEntity WHERE id IN (:ids)")
    suspend fun getByIds(ids: List<Long>): List<TranslationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<TranslationEntity>)
}
