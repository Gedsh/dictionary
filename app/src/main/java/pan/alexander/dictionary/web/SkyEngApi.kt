package pan.alexander.dictionary.web

import pan.alexander.dictionary.data.remote.pojo.SearchResponsePojo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SkyEngApi {

    @GET("words/search")
    suspend fun search(
        @Query("search") wordToSearch: String
    ): Response<List<SearchResponsePojo>>
}
