import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import pan.alexander.core_db.database.AppDatabase
import pan.alexander.core_db.database.LongListTypeConverter
import java.io.IOException

@ExperimentalCoroutinesApi
abstract class BaseDatabaseTest {
    protected lateinit var db: AppDatabase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun baseSetup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .addTypeConverter(LongListTypeConverter())
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun baseTearDown() {
        db.close()
        Dispatchers.resetMain()
    }
}
