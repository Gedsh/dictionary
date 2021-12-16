package pan.alexander.core_utils.logger

import android.util.Log
import pan.alexander.core_utils.Constants.LOG_TAG

object AppLogger {
    fun logE(message: String, throwable: Throwable?) = Log.e(LOG_TAG, message, throwable)
}
