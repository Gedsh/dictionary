package pan.alexander.dictionary.data.remote.pojo

import com.google.gson.annotations.SerializedName

data class MeaningsPojo(
    @SerializedName("id") val id: Int?,
    @SerializedName("translation") val translation: TranslationPojo?,
    @SerializedName("imageUrl") val imageUrl: String?
)
