package cat.rubenzu03.catbrary.ui.viewmodel

import android.content.Context
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
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class CreateCatViewModel(private val repo: CatRepository, private val context: Context) : ViewModel() {
    var name by mutableStateOf("")
    var age by mutableStateOf("")
    var selectedBreed by mutableStateOf<CatBreeds?>(null)
    var imageUri by mutableStateOf<Uri?>(null)

    var cats by mutableStateOf<List<Cat>>(emptyList())
        private set

    var isEditMode by mutableStateOf(false)
        private set

    var errorMessageResId by mutableStateOf<Int?>(null)

    private fun saveImageToInternalStorage(uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val fileName = "cat_${UUID.randomUUID()}.jpg"
            val file = File(context.filesDir, fileName)

            inputStream?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }

            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun isValid(): Boolean {
        if (name.isNotBlank() || age.isNotBlank() || selectedBreed != null || imageUri != null) {
            if (age.isNotBlank()) {
                val ageInt = age.toIntOrNull()
                if (ageInt == null || ageInt <= 0) {
                    errorMessageResId = cat.rubenzu03.catbrary.R.string.cat_dialog_name_error
                    return false
                }
            }
            errorMessageResId = null
            return true
        }
        errorMessageResId = cat.rubenzu03.catbrary.R.string.cat_dialog_empty_error
        return false
    }

    fun saveCat(): Boolean {
        if (!isValid()) return false
        val catName = name.trim()
        val catAge = age.toIntOrNull() ?: 0
        val catBreed = selectedBreed ?: CatBreeds.NONE

        val catImage = imageUri?.let { uri ->
            saveImageToInternalStorage(uri)
        } ?: ""

        val newCat = Cat(catName, catAge, catBreed, catImage)
        viewModelScope.launch {
            repo.insertCat(newCat)
            loadAllCats()
            clearForm()
        }
        return true
    }

    fun clearForm() {
        name = ""
        age = ""
        selectedBreed = null
        imageUri = null
    }

    private fun deleteImageFile(imagePath: String) {
        if (imagePath.isNotEmpty()) {
            try {
                val file = File(imagePath)
                if (file.exists()) {
                    file.delete()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadAllCats() {
        viewModelScope.launch {
            cats = repo.getAllCats()
        }
    }

    fun toggleEditMode() {
        isEditMode = !isEditMode
    }

    fun deleteCat(cat: Cat) {
        viewModelScope.launch {
            deleteImageFile(cat.image)
            repo.deleteCat(cat)
            loadAllCats()
        }
    }
}