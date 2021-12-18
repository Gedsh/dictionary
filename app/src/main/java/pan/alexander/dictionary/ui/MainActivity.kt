package pan.alexander.dictionary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.getKoin
import pan.alexander.core_ui.delegates.replace
import pan.alexander.dictionary.R
import pan.alexander.dictionary.di.ACTIVITY_RETAINED_SCOPE
import pan.alexander.dictionary.ui.history.HistoryFragment

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        activateHomeButtonIfNeeded()

        initBackStackChangedListener()

        if (savedInstanceState == null) {
            val transaction by replace(HistoryFragment.newInstance())
            transaction.commitNow()
        }
    }

    private fun initBackStackChangedListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            activateHomeButtonIfNeeded()
        }
    }

    private fun activateHomeButtonIfNeeded() {
        val stackHeight = supportFragmentManager.backStackEntryCount
        supportActionBar?.let {
            if (stackHeight > 0) {
                it.setHomeButtonEnabled(true)
                it.setDisplayHomeAsUpEnabled(true)
            } else {
                it.setHomeButtonEnabled(false)
                it.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val fragmentManager = supportFragmentManager
                if (fragmentManager.backStackEntryCount > 0) {
                    fragmentManager.popBackStack()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()

        if (!isChangingConfigurations) {
            getKoin().getScopeOrNull(ACTIVITY_RETAINED_SCOPE)?.close()
        }
    }
}
