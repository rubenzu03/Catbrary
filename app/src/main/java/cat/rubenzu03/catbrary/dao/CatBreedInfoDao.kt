package cat.rubenzu03.catbrary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cat.rubenzu03.catbrary.domain.CatBreedInfo

@Dao
interface CatBreedInfoDao {
    @Query("SELECT * FROM cat_breed_info")
    suspend fun getAllBreeds(): List<CatBreedInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(breeds: List<CatBreedInfo>)

    @Query("DELETE FROM cat_breed_info")
    suspend fun clearAll()
}

