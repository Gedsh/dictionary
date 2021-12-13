package pan.alexander.dictionary.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("word")])
data class SearchResponseEntity(
    @PrimaryKey
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "translation_ids") val translationIds: List<Long>
)
