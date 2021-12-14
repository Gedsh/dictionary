package pan.alexander.dictionary.domain.entities

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(indices = [Index("id")])
data class MeaningEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "translation") val translation: String,
    @ColumnInfo(name = "preview_url") val previewUrl: String,
    @ColumnInfo(name = "img_url") val imgUrl: String,
    @ColumnInfo(name = "transcription") val transcription: String,
    @ColumnInfo(name = "sound_url") val soundUrl: String
) : Parcelable
