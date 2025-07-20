package cat.rubenzu03.catbrary.ui.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cat.rubenzu03.catbrary.domain.Cat
import cat.rubenzu03.catbrary.domain.CatBreeds

@Composable
fun CatList(
    cats: List<Cat>,
    modifier: Modifier,
    isEditMode: Boolean = false,
    onDeleteCat: (Cat) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(cats) { cat ->
            CatItem(cat, modifier = Modifier, isEditMode = isEditMode, onDeleteCat = onDeleteCat)
        }
    }
}

@Preview
@Composable
fun CatListPreview() {
    val cats = listOf(
        Cat(name = "Tom", age = 2, breed = CatBreeds.Bengal, image = ""),
        Cat(name = "Jerry", age = 1, breed = CatBreeds.Himalayan, image = ""),
        Cat(name = "Garfield", age = 5, breed = CatBreeds.Persian, image = "")
    )
    CatList(
        cats = cats,
        modifier = Modifier,
        isEditMode = false,
        onDeleteCat = {}
    )
}