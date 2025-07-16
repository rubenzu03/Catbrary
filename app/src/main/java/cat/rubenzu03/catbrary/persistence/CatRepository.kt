package cat.rubenzu03.catbrary.persistence

import android.content.Context
import cat.rubenzu03.catbrary.dao.CatDao
import cat.rubenzu03.catbrary.domain.Cat

class CatRepository(private val catDao: CatDao) {
    suspend fun getAllCats() = catDao.getAllCats()

    //suspend fun getCatBreedsCount() = catDao.getCatBreedsCount()

    suspend fun insertCat(cat: Cat) = catDao.insertCat(cat)

    companion object{
        @Volatile
        private var INSTANCE: CatRepository? = null

        fun getInstance(context: Context): CatRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CatRepository(
                    AppDatabase.getDatabase(context).catDao()
                ).also { INSTANCE = it }
            }
        }
    }
}