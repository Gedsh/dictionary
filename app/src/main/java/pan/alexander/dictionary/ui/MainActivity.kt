package pan.alexander.dictionary.ui

import android.animation.ObjectAnimator
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.get
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.getKoin
import pan.alexander.core_ui.delegates.replace
import pan.alexander.dictionary.R
import pan.alexander.dictionary.databinding.MainActivityBinding
import pan.alexander.dictionary.di.ACTIVITY_RETAINED_SCOPE
import pan.alexander.dictionary.ui.history.HistoryFragment

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(MainActivityBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            changeSplashAnimation()
            changeSplashAnimationDuration()
        }

        setContentView(R.layout.main_activity)

        activateHomeButtonIfNeeded()

        initBackStackChangedListener()

        if (savedInstanceState == null) {
            val transaction by replace(HistoryFragment.newInstance())
            transaction.commitNow()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun changeSplashAnimation() {
        val blurEffect = RenderEffect.createBlurEffect(16f, 16f, Shader.TileMode.MIRROR)

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            ObjectAnimator.ofFloat(
                splashScreenView,
                View.ALPHA,
                1f,
                0f
            ).apply {
                splashScreenView.setBackgroundColor(
                    ContextCompat.getColor(this@MainActivity, android.R.color.transparent)
                )
                interpolator = AccelerateInterpolator()
                duration = 1000L
                binding.container[0].setRenderEffect(blurEffect)
                doOnEnd {
                    this@MainActivity.recreate()
                    splashScreenView.remove()
                    binding.container[0].setRenderEffect(null)
                    splashScreen.clearOnExitAnimationListener()
                }
                start()
            }

        }
    }

    private fun changeSplashAnimationDuration() {
        var isHideSplashScreen = false

        object : CountDownTimer(1000, 500) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                isHideSplashScreen = true
            }
        }.start()


        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isHideSplashScreen) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
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
