package pan.alexander.core_utils.converters

import pan.alexander.core_utils.Constants

object LongListStringConverter {
    private const val LIST_ITEM_SEPARATOR = ","

    fun listToString(list: List<Long>): String = list.joinToString(LIST_ITEM_SEPARATOR)

    fun stringToList(string: String): List<Long> = string.split(LIST_ITEM_SEPARATOR)
        .filter { it.matches(Constants.NUMBER_REGEX) }
        .map { it.toLong() }
}
