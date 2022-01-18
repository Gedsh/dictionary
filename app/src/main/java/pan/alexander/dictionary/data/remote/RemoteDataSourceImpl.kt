package pan.alexander.dictionary.data.remote

import kotlinx.coroutines.withContext
import pan.alexander.core_utils.coroutines.DispatcherProvider
import pan.alexander.core_web.pojo.SearchResponsePojo
import pan.alexander.core_web.web.SkyEngApi
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
