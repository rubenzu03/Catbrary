package cat.rubenzu03.catbrary.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cat.rubenzu03.catbrary.api.CatBreedApiRequest
import cat.rubenzu03.catbrary.domain.CatBreedInfo
import com.android.volley.VolleyError
import cat.rubenzu03.catbrary.persistence.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatBreedListViewModel(application: Application) : AndroidViewModel(application) {
    private val _breeds = MutableStateFlow<List<CatBreedInfo>>(emptyList())
    val breeds: StateFlow<List<CatBreedInfo>> = _breeds
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val api = CatBreedApiRequest(application)
    private val db = AppDatabase.getDatabase(application)
    private val breedDao = db.catBreedInfoDao()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val localBreeds = breedDao.getAllBreeds()
            if (localBreeds.isNotEmpty()) {
                _breeds.value = localBreeds
            }
        }
    }

    fun fetchBreeds() {
        // Solo hace fetch si no hay datos y no hay error
        if (_breeds.value.isNotEmpty() || _error.value != null) {
            return
        }
        _loading.value = true
        api.fetchAllCatBreedsInfo(
            url = "https://api.thecatapi.com/v1/breeds",
            onSuccess = { list ->
                val breedsWithImages = mutableListOf<CatBreedInfo>()
                var remaining = list.size
                if (list.isEmpty()) {
                    _breeds.value = list
                    _loading.value = false
                    return@fetchAllCatBreedsInfo
                }
                list.forEach { breed ->
                    api.fetchImageUrl(breed.refImageid,
                        onSuccess = { imageUrl ->
                            val breedWithImage = breed.copy(imageUrl = imageUrl)
                            breedsWithImages.add(breedWithImage)
                            remaining--
                            if (remaining == 0) {
                                viewModelScope.launch(Dispatchers.IO) {
                                    breedDao.clearAll()
                                    breedDao.insertAll(breedsWithImages)
                                    _breeds.value = breedsWithImages
                                    _loading.value = false
                                }
                            }
                        },
                        onError = {
                            breedsWithImages.add(breed)
                            remaining--
                            if (remaining == 0) {
                                viewModelScope.launch(Dispatchers.IO) {
                                    breedDao.clearAll()
                                    breedDao.insertAll(breedsWithImages)
                                    _breeds.value = breedsWithImages
                                    _loading.value = false
                                }
                            }
                        }
                    )
                }
            },
            onError = { err: VolleyError ->
                _error.value = err.message
                _loading.value = false
            }
        )
    }
}
