package pan.alexander.dictionary.espresso

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pan.alexander.dictionary.R
import pan.alexander.dictionary.test.EspressoIdlingResource
import pan.alexander.dictionary.tests.WORD
import pan.alexander.dictionary.ui.MainActivity

@LargeTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserScenarioEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var idlingResource: EspressoIdlingResource

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java).also {
            it.onActivity { activity ->
                idlingResource = activity.espressoIdlingResource
                IdlingRegistry.getInstance().register(idlingResource)
            }
        }
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        scenario.close()
    }

    @Test
    fun userScenario_RequestTranslations() {
        onView(ViewMatchers.withId(R.id.search_fab)).perform(click())
        onView(ViewMatchers.withId(R.id.search_edit_text))
            .perform(typeText(WORD), pressImeActionButton())
        onView(ViewMatchers.withId(R.id.translation_recyclerview))
            .check(ViewAssertions.matches(isDisplayed()))
    }
}
