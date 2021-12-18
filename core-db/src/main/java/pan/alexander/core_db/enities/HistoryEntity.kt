package pan.alexander.core_db.enities

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Keep
@Entity(indices = [Index("word")])
data class HistoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "timestamp", defaultValue = "CURRENT_TIMESTAMP") val timestamp: Long = 0
)
