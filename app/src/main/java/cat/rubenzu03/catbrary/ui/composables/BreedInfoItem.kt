package cat.rubenzu03.catbrary.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cat.rubenzu03.catbrary.domain.CatBreedInfo

@Composable
fun BreedInfoItem(breed: CatBreedInfo, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = breed.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Origen: ${breed.origin}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = breed.temperament, style = MaterialTheme.typography.bodySmall)
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Mostrar menos" else "Mostrar más"
                )
            }
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ),
                exit = shrinkVertically(
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                )
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = breed.description ?: "", style = MaterialTheme.typography.bodySmall)
                    //breed.lifeSpan?.let { Text(text = "Esperanza de vida: $it años", style = MaterialTheme.typography.bodySmall) }
                    Text(text = "Inteligencia: ${breed.intelligence}", style = MaterialTheme.typography.bodySmall)
                    Text(text = "Adaptabilidad: ${breed.adaptability}", style = MaterialTheme.typography.bodySmall)
                    Text(text = "Nivel de afecto: ${breed.affectionLevel}", style = MaterialTheme.typography.bodySmall)
                    Text(text = "Amigable con niños: ${breed.childFriendly}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun CatBreedListScreen(breeds: List<CatBreedInfo>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(breeds) { breed ->
            BreedInfoItem(breed = breed)
        }
    }
}
