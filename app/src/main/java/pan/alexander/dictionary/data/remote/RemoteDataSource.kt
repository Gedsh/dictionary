package pan.alexander.dictionary.data.remote

import pan.alexander.dictionary.data.remote.pojo.SearchResponsePojo
import retrofit2.Response

interface RemoteDataSource {
    suspend fun requestTranslations(word: String): Response<List<SearchResponsePojo>>
}
