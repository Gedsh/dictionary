package pan.alexander.dictionary.data.remote

import pan.alexander.dictionary.data.remote.pojo.ErrorResponsePojo
import pan.alexander.dictionary.domain.RemoteRepository
import pan.alexander.dictionary.domain.dto.TranslationDto
import retrofit2.Retrofit
import java.io.IOException

class RemoteRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val retrofit: Retrofit
) : RemoteRepository {

    private val converter by lazy {
        retrofit.responseBodyConverter<ErrorResponsePojo>(ErrorResponsePojo::class.java, arrayOf())
    }

    override suspend fun requestTranslations(word: String): List<TranslationDto> =
        remoteDataSource.requestTranslations(word)
            .let { response ->
                if (response.isSuccessful) {
                    response.body()?.filter {
                        it.id != null
                    }?.map { TranslationPojoToDtoMapper.map(it) }
                        ?: emptyList()
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
