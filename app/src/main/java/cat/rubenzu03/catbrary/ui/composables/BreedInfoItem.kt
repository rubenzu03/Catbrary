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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.rubenzu03.catbrary.domain.CatBreedInfo
import cat.rubenzu03.catbrary.R
import coil.compose.AsyncImage
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale

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
            AsyncImage(
                model = breed.imageUrl.ifBlank { null },
                contentDescription = breed.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(MaterialTheme.shapes.medium),
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = breed.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = stringResource(R.string.origin) + ": ${breed.origin}", style = MaterialTheme.typography.bodyMedium)
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
                    Text(text = breed.description, style = MaterialTheme.typography.bodySmall)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.intelligence), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.width(8.dp))
                        StarRate(rating = breed.intelligence, max = 5)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.adaptability), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.width(8.dp))
                        StarRate(rating = breed.adaptability, max = 5)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.affection_level), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.width(8.dp))
                        StarRate(rating = breed.affectionLevel, max = 5)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.child_friendly), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.width(8.dp))
                        StarRate(rating = breed.childFriendly, max = 5)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(text = stringResource(R.string.dog_friendly), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.width(8.dp))
                        StarRate(rating = breed.dogFriendly, max = 5)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.energy_level), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.width(8.dp))
                        StarRate(rating = breed.energyLevel, max = 5)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.grooming), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.width(8.dp))
                        StarRate(rating = breed.grooming, max = 5)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.health_issues), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.width(8.dp))
                        StarRate(rating = breed.healthIssues, max = 5)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.shedding_level), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.width(8.dp))
                        StarRate(rating = breed.sheddingLevel, max = 5)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.social_needs), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.width(8.dp))
                        StarRate(rating = breed.socialNeeds, max = 5)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.stranger_friendly), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.width(8.dp))
                        StarRate(rating = breed.strangerFriendly, max = 5)
                    }

                }
            }
        }
    }
}

@Composable
fun CatBreedListScreen(breeds: List<CatBreedInfo>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(breeds.filter { it.imageUrl.isNotBlank() }) { breed ->
            BreedInfoItem(breed = breed)
        }
    }
}

@Composable
fun StarRate(rating: Int, max: Int = 5) {
    Row {
        repeat(rating ) { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (index < rating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        repeat(max - rating) { index ->
            Icon(
                imageVector = Icons.Default.StarBorder,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
