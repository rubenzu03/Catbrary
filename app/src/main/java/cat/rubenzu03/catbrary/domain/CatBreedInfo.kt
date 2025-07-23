package cat.rubenzu03.catbrary.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_breed_info")
data class CatBreedInfo(
    @PrimaryKey val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val indoor: Int,
    val adaptability: Int,
    val affectionLevel: Int,
    val childFriendly: Int,
    val dogFriendly: Int,
    val energyLevel: Int,
    val grooming: Int,
    val healthIssues: Int,
    val intelligence: Int,
    val sheddingLevel: Int,
    val socialNeeds: Int,
    val strangerFriendly: Int,
    val wikipediaUrl: String,
    val refImageid: String,
    val imageUrl: String
)
