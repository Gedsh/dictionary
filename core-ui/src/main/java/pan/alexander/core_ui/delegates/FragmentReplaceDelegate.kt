package pan.alexander.core_ui.delegates

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import pan.alexander.core_ui.R
import kotlin.reflect.KProperty

class FragmentReplaceDelegate(
    private val fragmentManager: FragmentManager,
    private val fragment: Fragment,
    @IdRes
    private val container: Int,
    private val addToBackStack: Boolean
) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): FragmentTransaction =
        fragmentManager.beginTransaction()
            .replace(container, fragment)
            .also {
                if (addToBackStack) {
                    it.addToBackStack(null)
                }
            }

}

fun Fragment.replace(fragment: Fragment): FragmentReplaceDelegate =
    FragmentReplaceDelegate(parentFragmentManager, fragment, R.id.container, true)

fun AppCompatActivity.replace(fragment: Fragment): FragmentReplaceDelegate =
    FragmentReplaceDelegate(supportFragmentManager, fragment, R.id.container, false)
