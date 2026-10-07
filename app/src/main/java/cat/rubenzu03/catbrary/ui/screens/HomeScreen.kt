package cat.rubenzu03.catbrary.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.ui.components.CatList
import cat.rubenzu03.catbrary.ui.components.EmptyState
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateCatViewModel,
    onAddCat: () -> Unit = {},
) {
    LaunchedEffect(Unit) {
        viewModel.loadAllCats()
    }

    val cats = viewModel.cats

    if (cats.isEmpty()) {
        EmptyState(
            icon = Icons.Filled.Add,
            titleRes = R.string.home_empty_title,
            bodyRes = R.string.home_empty,
            modifier = modifier.fillMaxSize(),
            actionLabelRes = R.string.add_cat,
            onAction = onAddCat,
        )
    } else {
        CatList(
            cats = cats,
            modifier = modifier,
            isEditMode = viewModel.isEditMode,
            onDeleteCat = viewModel::deleteCat,
            onToggleFavorite = viewModel::toggleFavorite,
        )
    }
}
