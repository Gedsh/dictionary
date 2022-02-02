import android.os.Build
import android.provider.UserDictionary.Words.WORD
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

    @Before
    fun setUp() {
        historyDao = db.historyDao()
    }

    @Test
    fun historyDao_GetHistoryWords() = runTest {
        historyDao.insertHistoryRecord(WORD)
        assertEquals(WORD, historyDao.getHistoryWords().first())
    }

    @Test
    fun historyDao_DeleteWord() = runTest {
        historyDao.insertHistoryRecord(WORD)
        historyDao.deleteWord(WORD)
        assertTrue(historyDao.getHistoryWords().isEmpty())
    }

    @Test
    fun historyDao_DeleteAllWords() = runTest {
        historyDao.insertHistoryRecord(WORD)
        historyDao.deleteAllWords()
        assertTrue(historyDao.getHistoryWords().isEmpty())
    }
}
