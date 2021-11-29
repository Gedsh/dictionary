package pan.alexander.dictionary.data.remote.pojo

import com.google.gson.annotations.SerializedName

data class ErrorResponsePojo(
    @SerializedName("title") val title: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("detail") val detail: String?
)
