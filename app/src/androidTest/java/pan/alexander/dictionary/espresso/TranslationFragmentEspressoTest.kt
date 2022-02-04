package pan.alexander.dictionary.espresso

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pan.alexander.dictionary.R
import pan.alexander.dictionary.ui.translation.TranslationFragment

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TranslationFragmentEspressoTest {
    private lateinit var scenario: FragmentScenario<TranslationFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(null, R.style.Theme_Dictionary)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun translationFragment_HasSearchFab() {
        scenario.onFragment { fragment ->
            val fab = fragment.view?.findViewById<FloatingActionButton>(R.id.search_fab)
            assertNotNull(fab)
        }
    }

    @Test
    fun translationFragment_SearchFabIsVisible() {
        onView(ViewMatchers.withId(R.id.search_fab))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun translationFragment_TranslationRecyclerIsVisible() {
        onView(ViewMatchers.withId(R.id.translation_recyclerview)).check(
            matches(
                ViewMatchers.withEffectiveVisibility(
                    ViewMatchers.Visibility.VISIBLE
                )
            )
        )
    }
}
