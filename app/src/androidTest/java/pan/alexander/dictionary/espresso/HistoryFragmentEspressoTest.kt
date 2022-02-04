package pan.alexander.dictionary.espresso

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pan.alexander.dictionary.R
import pan.alexander.dictionary.ui.history.HistoryFragment

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HistoryFragmentEspressoTest {

    private lateinit var scenario: FragmentScenario<HistoryFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(null, R.style.Theme_Dictionary)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun historyFragment_HasSearchFab() {
        scenario.onFragment { fragment ->
            val fab = fragment.view?.findViewById<FloatingActionButton>(R.id.search_fab)
            assertNotNull(fab)
        }
    }

    @Test
    fun historyFragment_SearchFabIsVisible() {
        onView(withId(R.id.search_fab)).check(matches(isDisplayed()))
    }

    @Test
    fun historyFragment_HistoryRecyclerIsGone() {
        onView(withId(R.id.history_recyclerview)).check(
            matches(
                withEffectiveVisibility(
                    Visibility.GONE
                )
            )
        )
    }
}
