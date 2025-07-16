package cat.rubenzu03.catbrary.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cat.rubenzu03.catbrary.dao.CatDao
import cat.rubenzu03.catbrary.domain.Cat

@Database(entities = [Cat::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao

    companion object {
        const val DATABASE_NAME = "catbrary_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build().also { INSTANCE = it }
            }
        }
    }
}