package pan.alexander.core_web.pojo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MeaningsPojo(
    @SerializedName("id") val id: Long?,
    @SerializedName("translation") val translation: TranslationPojo?,
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("transcription") val transcription: String?,
    @SerializedName("soundUrl") val soundUrl: String?
)
