package pan.alexander.dictionary.automator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class InitialTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Test
    fun test_DeviceNotNull() {
        val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
        assertNotNull(uiDevice)
    }

    @Test
    fun test_AppPackageNotNull() {
        assertNotNull(packageName)
    }

    @Test
    fun test_MainActivityIntentNotNull() {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        assertNotNull(intent)
    }
}
