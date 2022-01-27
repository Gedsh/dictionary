import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import pan.alexander.core_db.database.HistoryDao

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class HistoryDaoTest : BaseDatabaseTest() {

    private lateinit var historyDao: HistoryDao

    private val word = "word"

    @Before
    fun setUp() {
        historyDao = db.historyDao()
    }

    @Test
    fun historyDao_GetHistoryWords() = runTest {
        historyDao.insertHistoryRecord(word)
        assertEquals(word, historyDao.getHistoryWords().first())
    }

    @Test
    fun historyDao_DeleteWord() = runTest {
        historyDao.insertHistoryRecord(word)
        historyDao.deleteWord(word)
        assertTrue(historyDao.getHistoryWords().isEmpty())
    }

    @Test
    fun historyDao_DeleteAllWords() = runTest {
        historyDao.insertHistoryRecord(word)
        historyDao.deleteAllWords()
        assertTrue(historyDao.getHistoryWords().isEmpty())
    }
}
