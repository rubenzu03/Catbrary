package cat.rubenzu03.catbrary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cat.rubenzu03.catbrary.domain.Cat

@Dao
interface CatDao {
    @Query("SELECT * FROM cats")
    suspend fun getAllCats(): List<Cat>

    @Query("SELECT * FROM cats WHERE name LIKE '%' || :searchQuery || '%'")
    suspend fun searchCatsByName(searchQuery: String): List<Cat>

    @Query("SELECT * FROM cats WHERE isFavorite = 1")
    suspend fun getFavoriteCats(): List<Cat>

    @Query("UPDATE cats SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Insert
    suspend fun insertCat(cat: Cat)

    @Delete
    suspend fun deleteCat(cat: Cat)
}
