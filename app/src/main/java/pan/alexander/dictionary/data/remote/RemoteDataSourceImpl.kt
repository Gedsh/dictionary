package pan.alexander.dictionary.data.remote

import kotlinx.coroutines.withContext
import pan.alexander.dictionary.data.remote.pojo.SearchResponsePojo
import pan.alexander.dictionary.utils.coroutines.DispatcherProvider
import pan.alexander.dictionary.web.SkyEngApi
import retrofit2.Response

class RemoteDataSourceImpl(
    private val skyEngApi: SkyEngApi,
    private val dispatcherProvider: DispatcherProvider
) : RemoteDataSource {
    override suspend fun requestTranslations(
        word: String
    ): Response<List<SearchResponsePojo>> = withContext(dispatcherProvider.io()) {
        skyEngApi.search(word)
    }
}
