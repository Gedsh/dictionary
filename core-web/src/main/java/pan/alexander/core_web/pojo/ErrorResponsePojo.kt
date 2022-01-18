package pan.alexander.core_web.pojo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorResponsePojo(
    @SerializedName("title") val title: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("detail") val detail: String?
)
