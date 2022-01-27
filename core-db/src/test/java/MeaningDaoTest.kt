import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import pan.alexander.core_db.database.MeaningDao
import pan.alexander.core_db.enities.MeaningEntity

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class MeaningDaoTest : BaseDatabaseTest() {

    private lateinit var meaningDao: MeaningDao

    private val id = 0L

    private val meaningEntity = MeaningEntity(
        id,
        "translation",
        "",
        "",
        "",
        ""
    )

    @Before
    fun setUp() {
        meaningDao = db.meaningDao()
    }

    @Test
    fun meaningDao_GetByIds() = runTest {
        meaningDao.insert(listOf(meaningEntity))
        assertEquals(meaningEntity, meaningDao.getByIds(listOf(id)).first())
    }
}
