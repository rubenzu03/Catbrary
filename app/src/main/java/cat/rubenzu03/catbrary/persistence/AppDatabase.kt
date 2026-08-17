package cat.rubenzu03.catbrary.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cat.rubenzu03.catbrary.dao.CatDao
import cat.rubenzu03.catbrary.dao.CatBreedInfoDao
import cat.rubenzu03.catbrary.domain.Cat
import cat.rubenzu03.catbrary.domain.CatBreedInfo

@Database(entities = [Cat::class, CatBreedInfo::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
    abstract fun catBreedInfoDao(): CatBreedInfoDao

    companion object {
        const val DATABASE_NAME = "catbrary_db"

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE cats ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
            }
        }

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).addMigrations(MIGRATION_2_3).build().also { INSTANCE = it }
            }
        }
    }
}