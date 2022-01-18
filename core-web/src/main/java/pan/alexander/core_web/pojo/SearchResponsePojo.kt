package pan.alexander.core_web.pojo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import pan.alexander.core_web.pojo.MeaningsPojo

@Keep
data class SearchResponsePojo(
    @SerializedName("id") val id: Long?,
    @SerializedName("text") val text: String?,
    @SerializedName("meanings") val meanings: List<MeaningsPojo>?
)
