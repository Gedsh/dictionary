package pan.alexander.dictionary.data.remote.pojo

import com.google.gson.annotations.SerializedName

data class MeaningsPojo(
    @SerializedName("id") val id: Long?,
    @SerializedName("translation") val translation: TranslationPojo?,
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("transcription") val transcription: String?,
    @SerializedName("soundUrl") val soundUrl: String?
)
