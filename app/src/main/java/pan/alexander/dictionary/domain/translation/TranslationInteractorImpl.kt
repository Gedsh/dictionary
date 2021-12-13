package pan.alexander.dictionary.domain.translation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.withContext
import pan.alexander.dictionary.domain.LocalRepository
import pan.alexander.dictionary.domain.NetworkRepository
import pan.alexander.dictionary.domain.RemoteRepository
import pan.alexander.dictionary.domain.dto.TranslationDto
import pan.alexander.dictionary.domain.entities.SearchResponseEntity
import pan.alexander.dictionary.domain.entities.TranslationEntity
import pan.alexander.dictionary.utils.coroutines.DispatcherProvider
import java.io.IOException
import kotlin.math.abs

private const val ERROR_RETRY_COUNT = 3

class TranslationInteractorImpl(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
    private val networkRepository: NetworkRepository,
    private val dispatcherProvider: DispatcherProvider
) : TranslationInteractor {

    override fun getTranslations(wordsFlow: SharedFlow<String>): Flow<TranslationResponseState> {

        return wordsFlow.map {
            val translationsFromDb = getTranslationsFromDb(it)
            when {
                translationsFromDb.isNotEmpty() -> {
                    TranslationResponseState.Success(sortTranslations(it, translationsFromDb))
                }
                networkRepository.isConnectionAvailable() -> {
                    val translationsFromApi = remoteRepository.requestTranslations(it)
                    saveTranslationsToDb(it, translationsFromApi)
                    TranslationResponseState.Success(sortTranslations(it, translationsFromApi))
                }
                else -> {
                    TranslationResponseState.NoConnection
                }
            }
        }.retryWhen { cause, attempt ->
            if (cause is IOException && attempt < ERROR_RETRY_COUNT) {
                delay(attempt * 100)
                true
            } else {
                false
            }
        }
    }

    private suspend fun getTranslationsFromDb(word: String): List<TranslationDto> =
        localRepository.getSearchResponseIdsByWord(word)
            .takeIf { it.isNotEmpty() }
            ?.let { localRepository.getTranslationsByIds(it) }
            ?.map {
                val meanings = localRepository.getMeaningByIds(it.meaningIds)
                TranslationDto(it.id, it.word, meanings)
            } ?: emptyList()

    private suspend fun saveTranslationsToDb(word: String, translations: List<TranslationDto>) {
        val translationIds = translations.map { it.id }
        localRepository.addSearchResponse(SearchResponseEntity(word, translationIds))

        val translationEntities = translations.map { dto ->
            TranslationEntity(
                dto.id,
                dto.word,
                dto.meanings.map { it.id }
            )
        }
        localRepository.addTranslations(translationEntities)

        translations.map { localRepository.addMeanings(it.meanings) }
    }

    private suspend fun sortTranslations(word: String, translations: List<TranslationDto>) =
        withContext(dispatcherProvider.io()) {
            translations.sortedBy {
                abs(it.word.length - word.length)
            }
        }

}
