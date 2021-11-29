package pan.alexander.dictionary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pan.alexander.dictionary.R
import pan.alexander.dictionary.ui.translation.TranslationFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TranslationFragment.newInstance())
                .commitNow()
        }
    }
}
