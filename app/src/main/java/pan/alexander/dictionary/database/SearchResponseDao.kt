package pan.alexander.dictionary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pan.alexander.dictionary.domain.entities.SearchResponseEntity

@Dao
interface SearchResponseDao {

    @Query("SELECT translation_ids FROM SearchResponseEntity WHERE word = :word")
    suspend fun getIdsByWord(word: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SearchResponseEntity)
}
