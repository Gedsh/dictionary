package pan.alexander.dictionary.domain.history

import java.io.IOException
import kotlin.jvm.Throws

interface HistoryInteractor {
    @Throws(IOException::class)
    suspend fun getHistory(): List<String>
    @Throws(IOException::class)
    suspend fun deleteWordFromHistory(word: String)
    @Throws(IOException::class)
    suspend fun clearHistory()
}
