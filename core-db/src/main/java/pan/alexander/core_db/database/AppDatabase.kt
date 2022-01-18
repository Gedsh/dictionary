package pan.alexander.core_db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pan.alexander.core_db.enities.MeaningEntity
import pan.alexander.core_db.enities.SearchResponseEntity
import pan.alexander.core_db.enities.TranslationEntity

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
