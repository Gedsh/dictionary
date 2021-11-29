package pan.alexander.dictionary.data.remote

import io.reactivex.rxjava3.core.Single
import pan.alexander.dictionary.data.remote.pojo.SearchResponsePojo
import retrofit2.Response

interface RemoteDataSource {
    fun requestTranslations(word: String): Single<Response<List<SearchResponsePojo>>>
}
