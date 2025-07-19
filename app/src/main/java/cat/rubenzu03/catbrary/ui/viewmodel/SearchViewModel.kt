package cat.rubenzu03.catbrary.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.rubenzu03.catbrary.domain.Cat
import cat.rubenzu03.catbrary.persistence.CatRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: CatRepository) : ViewModel() {
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _searchResults = mutableStateOf<List<Cat>>(emptyList())
    val searchResults: State<List<Cat>> = _searchResults

    private val _isSearching = mutableStateOf(false)
    val isSearching: State<Boolean> = _isSearching

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isNotBlank()) {
            searchCats(query)
        } else {
            _searchResults.value = emptyList()
        }
    }

    private fun searchCats(query: String) {
        viewModelScope.launch {
            _isSearching.value = true
            try {
                val results = repository.searchCatsByName(query)
                _searchResults.value = results
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            } finally {
                _isSearching.value = false
            }
        }
    }
}
