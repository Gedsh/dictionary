package pan.alexander.dictionary.domain.history

interface HistoryInteractor {
    suspend fun getHistory(): List<String>
    suspend fun deleteWordFromHistory(word: String)
    suspend fun clearHistory()
}
