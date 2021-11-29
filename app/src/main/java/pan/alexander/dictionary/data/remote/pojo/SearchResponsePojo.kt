package pan.alexander.dictionary.data.remote.pojo

import com.google.gson.annotations.SerializedName

data class SearchResponsePojo(
    @SerializedName("text") val text: String?,
    @SerializedName("meanings") val meanings: List<MeaningsPojo>?
)
