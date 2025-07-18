package cat.rubenzu03.catbrary.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.rubenzu03.catbrary.persistence.CatRepository

class CreateCatViewModelFactory(
    private val repo: CatRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateCatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateCatViewModel(repo, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}