package cat.rubenzu03.catbrary.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.rubenzu03.catbrary.persistence.CatRepository

class CreateCatViewModelFactory(
    private val repo: CatRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateCatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateCatViewModel(repo, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
