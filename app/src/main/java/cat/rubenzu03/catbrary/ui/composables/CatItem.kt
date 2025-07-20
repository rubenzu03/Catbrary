package cat.rubenzu03.catbrary.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.rubenzu03.catbrary.domain.Cat
import cat.rubenzu03.catbrary.domain.CatBreeds
import coil.compose.AsyncImage

@Composable
fun CatItem(cat: Cat, modifier: Modifier, isEditMode: Boolean = false, onDeleteCat: (Cat) -> Unit = {}){
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            leadingContent = {
                if (cat.image.isNotEmpty()) {
                    AsyncImage(
                        model = cat.image,
                        contentDescription = "Cat photo",
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            },
            headlineContent = {
                Text(
                    cat.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            supportingContent = {
                Text(
                    cat.breed.name.replace('_', ' ').lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            trailingContent = {
                if (isEditMode) {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete cat",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                } else {
                    Text(
                        "Age: ${cat.age}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        )
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                onDeleteCat(cat)
                showDeleteDialog = false
            }
        )
    }
}

@Preview
@Composable
fun CatItemPreview() {
    val cat = Cat(name = "Whiskers", age = 3, breed = CatBreeds.Siamese, image = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg")
    CatItem(cat = cat, modifier = Modifier)
}

@Preview
@Composable
fun CatItemEditModePreview() {
    val cat = Cat(name = "Shadow", age = 5, breed = CatBreeds.Maine_Coon, image = "")
    CatItem(cat = cat, modifier = Modifier, isEditMode = true, onDeleteCat = {})
}
