package com.rubenzu03.catbrary.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
class Cat {
    @PrimaryKey(autoGenerate = true) val id: Int = 0
    var name: String = ""
    var age: Int = 0
    var breed: CatBreeds = CatBreeds.NONE
    var image: String = ""


}