package cat.rubenzu03.catbrary.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cat.rubenzu03.catbrary.domain.Cat
import coil.compose.AsyncImage

@Composable
fun CatItem(cat: Cat, modifier: Modifier){
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ListItem(
            leadingContent = {
                if (cat.image.isNotEmpty()) {
                    AsyncImage(
                        model = cat.image,
                        contentDescription = "Cat photo",
                        modifier = Modifier.size(56.dp)
                    )
                }
            },
            headlineContent = { Text(cat.name, style = MaterialTheme.typography.titleLarge) },
            supportingContent = { Text(cat.breed.name.replace('_', ' ').lowercase().replaceFirstChar { it.uppercase() }) },
            trailingContent = { Text("Age: ${cat.age}", style = MaterialTheme.typography.bodyMedium) }
        )
    }
}