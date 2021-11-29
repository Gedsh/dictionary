package pan.alexander.dictionary.data.remote.pojo

import com.google.gson.annotations.SerializedName

data class TranslationPojo(
    @SerializedName("text") val translation: String?
)
