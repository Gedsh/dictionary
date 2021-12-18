package pan.alexander.core_db.database

import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT word FROM HistoryEntity ORDER BY timestamp DESC")
    suspend fun getHistoryWords(): List<String>

    @Query("INSERT OR REPLACE INTO HistoryEntity (word) VALUES (:word)")
    suspend fun insertHistoryRecord(word: String)

    @Query("DELETE FROM HistoryEntity WHERE word = :word")
    suspend fun deleteWord(word: String)

    @Query("DELETE FROM HistoryEntity")
    suspend fun deleteAllWords()
}
