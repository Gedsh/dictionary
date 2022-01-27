package pan.alexander.dictionary.data.remote

import pan.alexander.core_web.pojo.SearchResponsePojo
import retrofit2.Response
import java.io.IOException
import kotlin.jvm.Throws

interface RemoteDataSource {
    @Throws(IOException::class)
    suspend fun requestTranslations(word: String): Response<List<SearchResponsePojo>>
}
