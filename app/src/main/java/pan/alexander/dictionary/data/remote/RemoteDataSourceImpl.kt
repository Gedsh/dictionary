package pan.alexander.dictionary.data.remote

import io.reactivex.rxjava3.core.Single
import pan.alexander.dictionary.data.remote.pojo.SearchResponsePojo
import pan.alexander.dictionary.web.SkyEngApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val skyEngApi: SkyEngApi
) : RemoteDataSource {
    override fun requestTranslations(word: String): Single<Response<List<SearchResponsePojo>>> =
        skyEngApi.search(word)
}