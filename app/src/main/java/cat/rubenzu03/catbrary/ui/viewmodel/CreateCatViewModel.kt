package cat.rubenzu03.catbrary.ui.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cat.rubenzu03.catbrary.domain.CatBreeds

class CreateCatViewModel : ViewModel(){
    var name by mutableStateOf("")
    var age by mutableStateOf("")
    var selectedBreed by mutableStateOf<CatBreeds?>(null)
    var imageUri by mutableStateOf<Uri?>(null)


    fun saveCat() {
    }
}