package cat.rubenzu03.catbrary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.rubenzu03.catbrary.persistence.CatRepository

class CreateCatViewModelFactory(
    private val repo: CatRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateCatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateCatViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}