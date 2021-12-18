package pan.alexander.dictionary.domain.history

import pan.alexander.dictionary.domain.LocalRepository

class HistoryInteractorImpl(
    private val localRepository: LocalRepository
) : HistoryInteractor {

    override suspend fun getHistory(): List<String> =
        localRepository.getHistory()

    override suspend fun deleteWordFromHistory(word: String) =
        localRepository.deleteWordFromHistory(word)

    override suspend fun clearHistory() =
        localRepository.clearHistory()
}
