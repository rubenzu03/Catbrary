package cat.rubenzu03.catbrary.ui.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.rubenzu03.catbrary.domain.Cat
import cat.rubenzu03.catbrary.domain.CatBreeds
import cat.rubenzu03.catbrary.persistence.CatRepository
import kotlinx.coroutines.launch

class CreateCatViewModel(private val repo: CatRepository) : ViewModel() {
    var name by mutableStateOf("")
    var age by mutableStateOf("")
    var selectedBreed by mutableStateOf<CatBreeds?>(null)
    var imageUri by mutableStateOf<Uri?>(null)

    var cats by mutableStateOf<List<Cat>>(emptyList())
        private set

    var isEditMode by mutableStateOf(false)
        private set

    fun saveCat() {
        val catName = name.trim()
        val catAge = age.toIntOrNull() ?: 0
        val catBreed = selectedBreed ?: CatBreeds.NONE
        val catImage = imageUri?.toString() ?: ""

        val newCat = Cat(catName, catAge, catBreed, catImage)
        viewModelScope.launch {
            repo.insertCat(newCat)
            loadAllCats()
        }
    }

    fun loadAllCats(){
        viewModelScope.launch {
            cats = repo.getAllCats()
        }
    }

    fun toggleEditMode() {
        isEditMode = !isEditMode
    }

    fun deleteCat(cat: Cat) {
        viewModelScope.launch {
            repo.deleteCat(cat)
            loadAllCats()
        }
    }
}