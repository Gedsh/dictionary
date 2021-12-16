package pan.alexander.dictionary.data.remote.pojo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchResponsePojo(
    @SerializedName("id") val id: Long?,
    @SerializedName("text") val text: String?,
    @SerializedName("meanings") val meanings: List<MeaningsPojo>?
)
