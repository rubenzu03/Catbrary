package cat.rubenzu03.catbrary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cat.rubenzu03.catbrary.domain.Cat

@Dao
interface CatDao {
    @Query("SELECT * FROM cats")
    suspend fun getAllCats(): List<Cat>

    @Query("SELECT breed, SUM(breed) FROM cats  WHERE breed != 'NONE' GROUP BY breed")
    suspend fun getCatBreedsCount(): List<Pair<String, Int>>

    @Insert
    suspend fun insertCat(cat: Cat)
}