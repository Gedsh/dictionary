package pan.alexander.dictionary.data.remote

import io.reactivex.rxjava3.core.Single
import pan.alexander.dictionary.data.remote.pojo.ErrorResponsePojo
import pan.alexander.dictionary.domain.RemoteRepository
import pan.alexander.dictionary.domain.entities.Translation
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val retrofit: Retrofit
) : RemoteRepository {

    private val converter by lazy {
        retrofit.responseBodyConverter<ErrorResponsePojo>(ErrorResponsePojo::class.java, arrayOf())
    }

    override fun requestTranslations(word: String): Single<List<Translation>> =
        remoteDataSource.requestTranslations(word)
            .map { response ->
                if (response.isSuccessful) {
                    response.body()?.map { TranslationMapper.map(it) } ?: emptyList()
                } else {
                    response.errorBody()?.let {
                        converter.runCatching {
                            convert(it)
                        }.onSuccess {
                            throw IOException("${it?.title} ${it?.status} ${it?.detail}")
                        }.onFailure {
                            throw IOException()
                        }
                        throw IOException()
                    } ?: throw IOException()
                }
            }
}
