package cat.rubenzu03.catbrary.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.domain.CatBreedInfo
import cat.rubenzu03.catbrary.ui.theme.CatbraryTheme
import cat.rubenzu03.catbrary.ui.theme.Spacing
import coil.compose.AsyncImage

@Composable
fun BreedInfoItem(breed: CatBreedInfo, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val expandedStateLabel = stringResource(R.string.cd_expanded_state)
    val collapsedStateLabel = stringResource(R.string.cd_collapsed_state)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.m, vertical = Spacing.xs)
            .semantics {
                stateDescription = if (expanded) expandedStateLabel else collapsedStateLabel
            },
        onClick = { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = if (expanded) {
                MaterialTheme.colorScheme.surfaceContainerHigh
            } else {
                MaterialTheme.colorScheme.surfaceContainerLow
            },
        ),
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Column(modifier = Modifier.padding(Spacing.m)) {
            AsyncImage(
                model = breed.imageUrl.ifBlank { null },
                contentDescription = stringResource(R.string.breed_photo_description, breed.name),
                modifier = Modifier
                    .fillMaxWidth()

                    .aspectRatio(16f / 10f)
                    .clip(MaterialTheme.shapes.medium),
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder),
                contentScale = ContentScale.Crop,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing.s),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = breed.name,
                        style = MaterialTheme.typography.titleLargeEmphasized,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = stringResource(R.string.breed_origin_format, breed.origin),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = Spacing.xxs),
                    )
                    Text(
                        text = breed.temperament,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = Spacing.xxs),
                    )
                }
                Icon(
                    imageVector = if (expanded) {
                        Icons.Filled.KeyboardArrowUp
                    } else {
                        Icons.Filled.KeyboardArrowDown
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

            val expandSpec = MaterialTheme.motionScheme.defaultSpatialSpec<IntSize>()
            val fadeSpec = MaterialTheme.motionScheme.defaultEffectsSpec<Float>()
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(animationSpec = expandSpec) +
                    fadeIn(animationSpec = fadeSpec),
                exit = shrinkVertically(animationSpec = expandSpec) +
                    fadeOut(animationSpec = fadeSpec),
            ) {
                Column {
                    Text(
                        text = breed.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = Spacing.m),
                    )

                    val traits = listOf(
                        R.string.intelligence to breed.intelligence,
                        R.string.adaptability to breed.adaptability,
                        R.string.affection_level to breed.affectionLevel,
                        R.string.child_friendly to breed.childFriendly,
                        R.string.dog_friendly to breed.dogFriendly,
                        R.string.energy_level to breed.energyLevel,
                        R.string.grooming to breed.grooming,
                        R.string.health_issues to breed.healthIssues,
                        R.string.shedding_level to breed.sheddingLevel,
                        R.string.social_needs to breed.socialNeeds,
                        R.string.stranger_friendly to breed.strangerFriendly,
                    )

                    Column(
                        modifier = Modifier.padding(top = Spacing.m),
                        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        traits.forEach { (labelRes, rating) ->
                            TraitRow(labelRes = labelRes, rating = rating)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TraitRow(labelRes: Int, rating: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(labelRes),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(end = Spacing.s),
        )
        StarRate(rating = rating)
    }
}

@Composable
fun CatBreedListScreen(
    breeds: List<CatBreedInfo>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(breeds) { breed ->
            BreedInfoItem(breed = breed, modifier = Modifier.animateItem())
        }
    }
}

@Composable
fun StarRate(rating: Int, max: Int = 5) {
    Row {
        repeat(rating) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        repeat((max - rating).coerceAtLeast(0)) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview
@Composable
private fun BreedInfoItemPreview() {
    CatbraryTheme {
        BreedInfoItem(
            breed = CatBreedInfo(
                id = "siam",
                name = "Siamese",
                temperament = "Talkative, sociable",
                origin = "Thailand",
                description = "One of the oldest and most recognisable cat breeds.",
                indoor = 1,
                adaptability = 5,
                affectionLevel = 5,
                childFriendly = 4,
                dogFriendly = 3,
                energyLevel = 5,
                grooming = 1,
                healthIssues = 2,
                intelligence = 5,
                sheddingLevel = 1,
                socialNeeds = 5,
                strangerFriendly = 4,
                wikipediaUrl = "",
                refImageid = "",
                imageUrl = "",
            ),
        )
    }
}
