package pan.alexander.dictionary.utils.logger

import android.util.Log
import pan.alexander.dictionary.App.Companion.LOG_TAG

object AppLogger {
    fun logE(message: String, throwable: Throwable?) = Log.e(LOG_TAG, message, throwable)
}
