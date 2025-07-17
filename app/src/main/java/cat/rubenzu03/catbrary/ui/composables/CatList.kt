package cat.rubenzu03.catbrary.ui.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cat.rubenzu03.catbrary.domain.Cat

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