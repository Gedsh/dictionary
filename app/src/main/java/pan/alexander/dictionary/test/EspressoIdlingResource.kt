package pan.alexander.dictionary.test

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean

class EspressoIdlingResource : IdlingResource {

    private var callback: IdlingResource.ResourceCallback? = null

    private val isIdle = AtomicBoolean(true)

    override fun getName(): String = javaClass.name

    override fun isIdleNow(): Boolean = isIdle.get()

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    fun setIsIdle(idle: Boolean) {
        isIdle.set(idle)
        callback?.let {
            if (isIdleNow) {
                it.onTransitionToIdle()
            }
        }
    }
}
