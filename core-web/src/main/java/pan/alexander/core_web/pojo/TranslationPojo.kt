package pan.alexander.core_web.pojo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TranslationPojo(
    @SerializedName("text") val translation: String?
)
