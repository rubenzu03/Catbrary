package cat.rubenzu03.catbrary.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExpandedDockedSearchBar
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.persistence.CatRepository
import cat.rubenzu03.catbrary.ui.components.CatList
import cat.rubenzu03.catbrary.ui.components.LoadingState
import cat.rubenzu03.catbrary.ui.theme.Spacing
import cat.rubenzu03.catbrary.ui.viewmodel.SearchViewModel
import cat.rubenzu03.catbrary.ui.viewmodel.SearchViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    isExpandedWidth: Boolean = false,
) {
    val context = LocalContext.current
    val repo = remember { CatRepository.getInstance(context) }
    val searchFactory = remember { SearchViewModelFactory(repo) }
    val searchViewModel: SearchViewModel = viewModel(factory = searchFactory)

    val searchResults by searchViewModel.searchResults
    val isSearching by searchViewModel.isSearching

    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        snapshotFlow { textFieldState.text.toString() }
            .collect { query -> searchViewModel.updateSearchQuery(query) }
    }

    val inputField: @Composable () -> Unit = {
        SearchBarDefaults.InputField(
            textFieldState = textFieldState,
            searchBarState = searchBarState,
            onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
            placeholder = { Text(stringResource(R.string.search_hint)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.cd_search),
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }

    val expandedContent: @Composable () -> Unit = {
        when {
            isSearching -> LoadingState(modifier = Modifier.fillMaxWidth())

            searchResults.isNotEmpty() -> CatList(
                cats = searchResults,
                modifier = Modifier.padding(top = Spacing.m),
                onCatClick = {
                    scope.launch { searchBarState.animateToCollapsed() }
                },
            )

            textFieldState.text.isNotEmpty() -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.m),
                contentAlignment = Alignment.Center,
            ) {
                Text(stringResource(R.string.search_no_results_format, textFieldState.text))
            }

            else -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.m),
                contentAlignment = Alignment.Center,
            ) {
                Text(stringResource(R.string.search_hint))
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.m),
    ) {
        SearchBar(
            state = searchBarState,
            inputField = inputField,
            modifier = Modifier.fillMaxWidth(),
        )
    }

    if (isExpandedWidth) {
        ExpandedDockedSearchBar(
            state = searchBarState,
            inputField = inputField,
            modifier = Modifier.padding(Spacing.m),
            content = { expandedContent() },
        )
    } else {
        ExpandedFullScreenSearchBar(
            state = searchBarState,
            inputField = inputField,
            modifier = Modifier,
            content = { expandedContent() },
        )
    }
}
