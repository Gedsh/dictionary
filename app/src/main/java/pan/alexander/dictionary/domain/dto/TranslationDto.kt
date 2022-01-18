package pan.alexander.dictionary.domain.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pan.alexander.core_db.enities.MeaningEntity

@Parcelize
data class TranslationDto(
    val id: Long,
    val word: String,
    val meanings: List<MeaningEntity>
) : Parcelable
