package com.rubenzu03.catbrary.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CreateCatViewModel : ViewModel(){
    var name by mutableStateOf("")
    var age by mutableStateOf("")
    var selectedBreed by mutableStateOf<CatBreeds?>(null)
    var imageUri by mutableStateOf<Uri?>(null)


    fun saveCat() {
        val catName = name.trim()
        val catAge = age.toIntOrNull() ?: 0
        val catBreed = selectedBreed ?: CatBreeds.NONE
        val catImage = imageUri?.toString() ?: ""

        val newCat = Cat(catName,catAge,catBreed,catImage)
        viewModelScope.launch {
            repository.insertCat(newCat)
        }
    }
}