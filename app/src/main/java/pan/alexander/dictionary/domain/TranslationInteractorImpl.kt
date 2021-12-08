package pan.alexander.dictionary.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

private const val ERROR_RETRY_COUNT = 3

class TranslationInteractorImpl(
    private val remoteRepository: RemoteRepository,
    private val networkRepository: NetworkRepository
) : TranslationInteractor {

    override fun getTranslations(flow: SharedFlow<String>) =
        if (networkRepository.isConnectionAvailable()) {
            flow.map {
                val translations = remoteRepository.requestTranslations(it)
                TranslationResponseState.Success(translations)
            }.retryWhen { cause, attempt ->
                if (cause is IOException && attempt < ERROR_RETRY_COUNT) {
                    delay(attempt * 100)
                    true
                } else {
                    false
                }
            }
        } else {
            flow.map { TranslationResponseState.NoConnection }
        }

}
