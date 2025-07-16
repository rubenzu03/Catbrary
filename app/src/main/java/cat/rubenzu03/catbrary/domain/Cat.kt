package cat.rubenzu03.catbrary.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
class Cat {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    var name: String = ""
    var age: Int = 0
    var breed: CatBreeds = CatBreeds.NONE
    var image: String = ""

    constructor(name: String, age: Int, breed: CatBreeds, image: String){
        this.name = name
        this.age = age
        this.breed = breed
        this.image = image
    }
}