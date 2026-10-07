package cat.rubenzu03.catbrary.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.ui.components.CatList
import cat.rubenzu03.catbrary.ui.components.EmptyState
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateCatViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.loadFavoriteCats()
    }

    val favoriteCats = viewModel.favoriteCats

    if (favoriteCats.isEmpty()) {

        EmptyState(
            icon = Icons.Filled.FavoriteBorder,
            titleRes = R.string.favorites_empty_title,
            bodyRes = R.string.favorites_empty,
            modifier = modifier.fillMaxSize(),
        )
    } else {
        CatList(
            cats = favoriteCats,
            modifier = modifier,
            onToggleFavorite = viewModel::toggleFavorite,
        )
    }
}
