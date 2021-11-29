package pan.alexander.dictionary.web

import io.reactivex.rxjava3.core.Single
import pan.alexander.dictionary.data.remote.pojo.SearchResponsePojo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SkyEngApi {

    @GET("words/search")
    fun search(
        @Query("search") wordToSearch: String
    ): Single<Response<List<SearchResponsePojo>>>
}
