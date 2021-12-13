package pan.alexander.dictionary.domain.entities

import androidx.annotation.Keep
import androidx.room.*

@Keep
@Entity(indices = [Index("id")])
data class TranslationEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "meaning_ids") val meaningIds: List<Long>,
)
