package pan.alexander.dictionary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pan.alexander.dictionary.domain.entities.MeaningEntity
import pan.alexander.dictionary.domain.entities.SearchResponseEntity
import pan.alexander.dictionary.domain.entities.TranslationEntity

@Database(
    entities = [SearchResponseEntity::class, TranslationEntity::class, MeaningEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LongListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchResponseDao(): SearchResponseDao
    abstract fun translationDao(): TranslationDao
    abstract fun meaningDao(): MeaningDao
}
