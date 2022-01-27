import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import pan.alexander.core_db.database.TranslationDao
import pan.alexander.core_db.enities.TranslationEntity

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class TranslationDaoTest : BaseDatabaseTest() {
    private lateinit var translationDao: TranslationDao

    private val translationEntity = TranslationEntity(
        0,
        "word",
        listOf(0)
    )

    @Before
    fun setUp() {
        translationDao = db.translationDao()
    }

    @Test
    fun translationDao_GetByIds() = runTest {
        translationDao.insert(listOf(translationEntity))
        assertEquals(translationEntity, translationDao.getByIds(listOf(0)).first())
    }
}
