package pan.alexander.dictionary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pan.alexander.dictionary.domain.entities.MeaningEntity

@Dao
interface MeaningDao {

    @Query("SELECT * FROM MeaningEntity WHERE id IN (:ids)")
    suspend fun getByIds(ids: List<Long>): List<MeaningEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<MeaningEntity>)
}
