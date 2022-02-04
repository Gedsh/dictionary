package pan.alexander.dictionary.espresso

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pan.alexander.core_db.enities.MeaningEntity
import pan.alexander.dictionary.R
import pan.alexander.dictionary.domain.dto.TranslationDto
import pan.alexander.dictionary.ui.details.DetailsFragment

private const val TRANSLATION_EXTRA = "pan.alexander.dictionary.TRANSLATION_EXTRA"

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DetailsFragmentEspressoTest {

    private lateinit var scenario: FragmentScenario<DetailsFragment>

    @Before
    fun setUp() {
        val meanings = mutableListOf<MeaningEntity>().apply {
            repeat(100) {
                add(
                    MeaningEntity(
                        it.toLong(),
                        "word$it",
                        "",
                        "",
                        "",
                        ""
                    )
                )
            }
        }
        val translationDto = TranslationDto(
            0,
            "word",
            meanings
        )
        val bundle = bundleOf(TRANSLATION_EXTRA to translationDto)
        scenario = launchFragmentInContainer(bundle, R.style.Theme_Dictionary)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun detailsFragment_DescriptionScreenRecycler_IsVisible() {
        onView(withId(R.id.description_screen_recycler)).check(
            matches(
                withEffectiveVisibility(
                    Visibility.VISIBLE
                )
            )
        )
    }

    @Test
    fun detailsFragment_ScrollTo() {
        onView(withId(R.id.description_screen_recycler))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("word80"))
                )
            )
    }
}
