package cat.rubenzu03.catbrary.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.rubenzu03.catbrary.domain.Cat
import cat.rubenzu03.catbrary.domain.CatBreeds
import coil.compose.AsyncImage

import cat.rubenzu03.catbrary.R


@Composable
fun CatItem(cat: Cat, modifier: Modifier, isEditMode: Boolean = false, onDeleteCat: (Cat) -> Unit = {}){
    var showDeleteDialog by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                ),
                leadingContent = {
                    if (cat.image.isNotEmpty()) {
                        var imageAspectRatio by remember { mutableStateOf(1f) }
                        val baseSize = if (isExpanded) 80.dp else 56.dp

                        AsyncImage(
                            model = cat.image,
                            contentDescription = "Cat photo",
                            modifier = Modifier
                                .width(baseSize)
                                .aspectRatio(imageAspectRatio)
                                .heightIn(max = baseSize * 1.5f)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop,
                            onSuccess = { success ->
                                val drawable = success.result.drawable
                                imageAspectRatio = drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight.toFloat()
                            }
                        )
                    }
                },
                headlineContent = {
                    Text(
                        cat.name,
                        style = if (isExpanded) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = if (isExpanded) FontWeight.Bold else FontWeight.Normal
                    )
                },
                supportingContent = {
                    Text(
                        cat.breed.displayName,
                        style = if (isExpanded) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingContent = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isEditMode) {
                            IconButton(onClick = { showDeleteDialog = true }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete cat",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        } else if (!isExpanded) {
                            Text(
                                stringResource(R.string.cat_item_age) + "${cat.age}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )

            AnimatedVisibility(
                visible = isExpanded,
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.cat_item_age),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "${cat.age} ${if (cat.age == 1) stringResource(R.string.cat_item_year) else stringResource(R.string.cat_item_years)} " + stringResource(R.string.cat_item_old),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.cat_item_breed),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            cat.breed.displayName,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (cat.image.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        var expandedImageAspectRatio by remember { mutableStateOf(1f) }

                        AsyncImage(
                            model = cat.image,
                            contentDescription = "Cat photo expanded",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(expandedImageAspectRatio)
                                .heightIn(min = 150.dp, max = 300.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop,
                            onSuccess = { success ->
                                val drawable = success.result.drawable
                                expandedImageAspectRatio = drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight.toFloat()
                            }
                        )
                    }
                }
            }
        }
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
