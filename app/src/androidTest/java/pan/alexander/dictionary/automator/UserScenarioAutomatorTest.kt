package pan.alexander.dictionary.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pan.alexander.dictionary.tests.TIMEOUT
import pan.alexander.dictionary.tests.WORD
import java.lang.Thread.sleep

private const val DELAY_WIFI_DISABLE = 1000L
private const val WORD_ONLINE = "dog"
private const val MEANING_ONLINE = "doggy"
private const val WORD_OFFLINE = WORD
private const val MEANING_OFFLINE = "wordy"
private const val WORD_NO_CONNECTION = "cat"
private const val SHELL_WIFI_DISABLE = "svc wifi disable"
private const val SHELL_WIFI_ENABLE = "svc wifi enable"

@LargeTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserScenarioAutomatorTest {

    private val uiDevice: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setUp() {
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase("main_database.db")

        uiDevice.pressHome()

        val launcherPackage: String = uiDevice.launcherPackageName
        ViewMatchers.assertThat(launcherPackage, CoreMatchers.notNullValue())
        uiDevice.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            TIMEOUT
        )

        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        uiDevice.wait(
            Until.hasObject(By.pkg(packageName).depth(0)),
            TIMEOUT
        )
    }

    @Test
    fun userScenario_RequestTranslations_OnLine() {

        uiDevice.findObject(By.res(packageName, "menu_clear_history")).click()

        uiDevice.findObject(By.res(packageName, "search_fab")).click()

        val searchEditText = uiDevice.wait(
            Until.findObject(By.res(packageName, "search_edit_text")),
            TIMEOUT
        )

        searchEditText.text = WORD_ONLINE

        uiDevice.findObject(By.res(packageName, "search_button_textview")).click()

        val translationHeaderTextView = uiDevice.wait(
            Until.findObject(
                By.text(MEANING_ONLINE)
            ), TIMEOUT
        )

        assertNotNull(translationHeaderTextView)
    }

    @Test
    fun userScenario_RequestTranslations_OffLine() {

        uiDevice.findObject(By.res(packageName, "menu_clear_history")).click()

        uiDevice.findObject(By.res(packageName, "search_fab")).click()

        val searchEditText = uiDevice.wait(
            Until.findObject(By.res(packageName, "search_edit_text")),
            TIMEOUT
        )

        searchEditText.text = WORD_OFFLINE

        uiDevice.findObject(By.res(packageName, "search_button_textview")).click()

        uiDevice.wait(
            Until.findObject(
                By.text(MEANING_OFFLINE)
            ), TIMEOUT
        )

        uiDevice.pressBack()

        val recycleTextView = uiDevice.wait(
            Until.findObject(
                By.text(WORD_OFFLINE)
            ), TIMEOUT
        )

        InstrumentationRegistry.getInstrumentation()
            .uiAutomation
            .executeShellCommand(SHELL_WIFI_DISABLE)
            .close()

        try {
            sleep(DELAY_WIFI_DISABLE)

            recycleTextView.parent.click()

            val translationHeaderTextView = uiDevice.wait(
                Until.findObject(
                    By.text(MEANING_OFFLINE)
                ), TIMEOUT
            )

            assertNotNull(translationHeaderTextView)

        } finally {
            InstrumentationRegistry.getInstrumentation()
                .uiAutomation
                .executeShellCommand(SHELL_WIFI_ENABLE)
        }

    }

    @Test
    fun userScenario_RequestTranslations_NoConnection() {
        InstrumentationRegistry.getInstrumentation()
            .uiAutomation
            .executeShellCommand(SHELL_WIFI_DISABLE)
            .close()

        try {
            sleep(DELAY_WIFI_DISABLE)

            uiDevice.findObject(By.res(packageName, "menu_clear_history")).click()

            uiDevice.findObject(By.res(packageName, "search_fab")).click()

            val searchEditText = uiDevice.wait(
                Until.findObject(By.res(packageName, "search_edit_text")),
                TIMEOUT
            )

            searchEditText.text = WORD_NO_CONNECTION

            uiDevice.findObject(By.res(packageName, "search_button_textview")).click()

            val errorTextView = uiDevice.wait(
                Until.findObject(
                    By.res(packageName, "error_textview")
                ), TIMEOUT
            )

            assertNotNull(errorTextView)

        } finally {
            InstrumentationRegistry.getInstrumentation()
                .uiAutomation
                .executeShellCommand(SHELL_WIFI_ENABLE)
        }

    }
}
