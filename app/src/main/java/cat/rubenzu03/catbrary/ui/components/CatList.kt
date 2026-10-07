package cat.rubenzu03.catbrary.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cat.rubenzu03.catbrary.domain.Cat
import cat.rubenzu03.catbrary.domain.CatBreeds
import cat.rubenzu03.catbrary.ui.theme.CatbraryTheme

@Composable
fun CatList(
    cats: List<Cat>,
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false,
    onCatClick: (Cat) -> Unit = {},
    onDeleteCat: (Cat) -> Unit = {},
    onToggleFavorite: (Cat) -> Unit = {},
) {
    LazyColumn(modifier = modifier) {
        items(cats) { cat ->
            CatItem(
                cat = cat,
                modifier = Modifier.animateItem(),
                isEditMode = isEditMode,
                onCatClick = onCatClick,
                onDeleteCat = onDeleteCat,
                onToggleFavorite = onToggleFavorite,
            )
        }
    }
}

@Preview
@Composable
private fun CatListPreview() {
    CatbraryTheme {
        CatList(
            cats = listOf(
                Cat("Tom", 2, CatBreeds.Bengal, ""),
                Cat("Jerry", 1, CatBreeds.Himalayan, ""),
                Cat("Garfield", 5, CatBreeds.Persian, ""),
            ),
        )
    }
}
