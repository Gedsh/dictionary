package pan.alexander.dictionary.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import pan.alexander.dictionary.domain.entities.Translation
import java.io.IOException

private const val ERROR_RETRY_COUNT = 3

class TranslationInteractorImpl(
    private val remoteRepository: RemoteRepository,
    private val networkRepository: NetworkRepository
) : TranslationInteractor {

    override suspend fun getTranslations(word: String): TranslationResponseState =
        if (networkRepository.isConnectionAvailable()) {
            requestTranslations(word).let { TranslationResponseState.Success(it) }
        } else {
            TranslationResponseState.NoConnection
        }

    private suspend fun requestTranslations(word: String): List<Translation> {
        var translations: List<Translation>? = null
        flowOf(word)
            .map { remoteRepository.requestTranslations(it) }
            .retryWhen { cause, attempt ->
                if (cause is IOException && attempt < ERROR_RETRY_COUNT) {
                    delay(attempt * 100)
                    true
                } else {
                    false
                }
            }.collect {
                translations = it
            }
        return translations ?: emptyList()
    }

}
