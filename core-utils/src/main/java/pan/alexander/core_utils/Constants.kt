package pan.alexander.core_utils

import java.util.regex.Pattern

object Constants {
    const val LOG_TAG = "dictionary"
    val NUMBER_REGEX = Regex("\\d+")
    val WORD_REGEX = Regex("\\w+")

    val EMAIL_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    val URL_PATTERN: Pattern = Pattern.compile(
        "https://[a-zA-Z0-9+._%\\-]{1,256}\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}(?:/[a-zA-Z0-9+._%\\-]{1,256})*/"
    )
}
