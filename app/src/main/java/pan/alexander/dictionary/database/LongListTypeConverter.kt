package pan.alexander.dictionary.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import pan.alexander.dictionary.utils.Constants.NUMBER_REGEX

const val LIST_ITEM_SEPARATOR = ","

@ProvidedTypeConverter
class LongListTypeConverter {

    @TypeConverter
    fun listToString(list: List<Long>): String = list.joinToString(LIST_ITEM_SEPARATOR)

    @TypeConverter
    fun stringToList(string: String): List<Long> = string.split(LIST_ITEM_SEPARATOR)
        .filter { it.matches(NUMBER_REGEX) }
        .map { it.toLong() }
}
