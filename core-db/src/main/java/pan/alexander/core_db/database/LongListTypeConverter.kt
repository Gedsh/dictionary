package pan.alexander.core_db.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import pan.alexander.core_utils.converters.LongListStringConverter

@ProvidedTypeConverter
class LongListTypeConverter {

    @TypeConverter
    fun listToString(list: List<Long>): String = LongListStringConverter.listToString(list)

    @TypeConverter
    fun stringToList(string: String): List<Long> = LongListStringConverter.stringToList(string)
}
