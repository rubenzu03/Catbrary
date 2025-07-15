package cat.rubenzu03.catbrary.domain

class Cat {
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

    constructor(name: String, age: Int, breed: CatBreeds){
        this.name = name
        this.age = age
        this.breed = breed
        this.image = ""
    }
}