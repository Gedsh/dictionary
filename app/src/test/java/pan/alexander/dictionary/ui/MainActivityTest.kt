package pan.alexander.dictionary.ui

import android.os.Build
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.annotation.Config
import pan.alexander.dictionary.R

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class MainActivityTest : AutoCloseKoinTest() {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = launchActivity()
    }

    @After
    fun close() {
        scenario.close()
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activity_HasTitleText() {
        scenario.onActivity {
            val title = it.title
            assertEquals(it.getString(R.string.history), title)
        }
    }

    @Test
    fun activity_HasSearchFab() {
        scenario.onActivity {
            val fab = it.findViewById<FloatingActionButton>(R.id.search_fab)
            assertNotNull(fab)
        }
    }

    @Test
    fun activity_SearchFabIsVisible() {
        scenario.onActivity {
            val fab = it.findViewById<FloatingActionButton>(R.id.search_fab)
            assertEquals(View.VISIBLE, fab.visibility)
        }
    }

    @Test
    fun activity_HistoryRecyclerIsGone() {
        scenario.onActivity {
            val recycler = it.findViewById<RecyclerView>(R.id.history_recyclerview)
            assertEquals(View.GONE, recycler.visibility)
        }
    }
}
