import android.os.Build
import android.provider.UserDictionary.Words.WORD
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import pan.alexander.core_db.database.SearchResponseDao
import pan.alexander.core_db.enities.SearchResponseEntity

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class SearchResponseDaoTest : BaseDatabaseTest() {

    private lateinit var searchResponseDao: SearchResponseDao

    private val id = 0L

    private val searchResponseEntity = SearchResponseEntity(
        WORD,
        listOf(id)
    )

    @Before
    fun setUp() {
        searchResponseDao = db.searchResponseDao()
    }

    @Test
    fun searchResponseDao_GetIdsByWord() = runTest {
        searchResponseDao.insert(searchResponseEntity)
        assertEquals(id, searchResponseDao.getIdsByWord(WORD)!!.toLong())
    }
}
