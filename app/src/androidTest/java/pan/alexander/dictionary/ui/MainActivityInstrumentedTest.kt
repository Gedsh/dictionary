package pan.alexander.dictionary.ui

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pan.alexander.dictionary.R

@LargeTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun close() {
        scenario.close()
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
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
        onView(withId(R.id.search_fab)).check(matches(isDisplayed()))
    }

    @Test
    fun activity_HistoryRecyclerIsVisible() {
        onView(withId(R.id.history_recyclerview)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

}
