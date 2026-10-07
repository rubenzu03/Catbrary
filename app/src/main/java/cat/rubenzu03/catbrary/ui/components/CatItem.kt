package cat.rubenzu03.catbrary.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.domain.Cat
import cat.rubenzu03.catbrary.domain.CatBreeds
import cat.rubenzu03.catbrary.ui.theme.CatbraryTheme
import cat.rubenzu03.catbrary.ui.theme.Spacing
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun CatItem(
    cat: Cat,
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false,
    onCatClick: (Cat) -> Unit = {},
    onDeleteCat: (Cat) -> Unit = {},
    onToggleFavorite: (Cat) -> Unit = {},
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val swipeState = rememberSwipeToDismissBoxState()
    val scope = rememberCoroutineScope()
    val haptics = LocalHapticFeedback.current

    val confirmDelete = {
        scope.launch { swipeState.snapTo(SwipeToDismissBoxValue.Settled) }
        showDeleteDialog = true
    }

    SwipeToDismissBox(
        state = swipeState,

        gesturesEnabled = !isEditMode,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Spacing.m, vertical = Spacing.xs)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = Spacing.l),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onErrorContainer,
                    )
                    Text(
                        text = stringResource(R.string.delete),
                        style = MaterialTheme.typography.labelLargeEmphasized,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(start = Spacing.xs),
                    )
                }
            }
        },
        onDismiss = { confirmDelete() },
    ) {
        CatCard(
            cat = cat,
            isEditMode = isEditMode,
            onCatClick = onCatClick,
            onDeleteCat = onDeleteCat,
            onToggleFavorite = onToggleFavorite,
            onRequestDelete = { showDeleteDialog = true },
            haptics = haptics,
        )
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                onDeleteCat(cat)
                showDeleteDialog = false
            },
        )
    }
}

@Composable
private fun CatCard(
    cat: Cat,
    isEditMode: Boolean,
    onCatClick: (Cat) -> Unit,
    onDeleteCat: (Cat) -> Unit,
    onToggleFavorite: (Cat) -> Unit,
    onRequestDelete: () -> Unit,
    haptics: HapticFeedback,
) {
    var isExpanded by remember { mutableStateOf(false) }

    val ageLabel = pluralStringResource(R.plurals.cat_age_years, cat.age, cat.age)
    val expandedStateLabel = stringResource(R.string.cd_expanded_state)
    val collapsedStateLabel = stringResource(R.string.cd_collapsed_state)

    Card(
        modifier = Modifier
            .padding(horizontal = Spacing.m, vertical = Spacing.xs)

            .semantics {
                stateDescription = if (isExpanded) expandedStateLabel else collapsedStateLabel
            },
        onClick = {
            haptics.performHapticFeedback(HapticFeedbackType.ToggleOn)
            onCatClick(cat)
            isExpanded = !isExpanded
        },

        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) {
                MaterialTheme.colorScheme.surfaceContainerHigh
            } else {
                MaterialTheme.colorScheme.surfaceContainerLow
            },
        ),
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Column {
            ListItem(

                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                leadingContent = {
                    if (cat.image.isNotEmpty()) {
                        var imageAspectRatio by remember { mutableFloatStateOf(1f) }
                        val baseSize = if (isExpanded) 80.dp else 56.dp

                        AsyncImage(
                            model = cat.image,
                            contentDescription = stringResource(
                                R.string.cat_photo_description,
                                cat.name,
                            ),
                            modifier = Modifier
                                .width(baseSize)
                                .aspectRatio(imageAspectRatio)
                                .heightIn(max = baseSize * 1.5f)
                                .clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop,
                            onSuccess = { success ->
                                val drawable = success.result.drawable
                                imageAspectRatio =
                                    drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight.toFloat()
                            },
                        )
                    }
                },
                supportingContent = {
                    Text(
                        cat.breed.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                trailingContent = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        FilledIconToggleButton(
                            checked = cat.isFavorite,
                            onCheckedChange = {
                                haptics.performHapticFeedback(HapticFeedbackType.ToggleOn)
                                onToggleFavorite(cat)
                            },
                            shapes = IconButtonDefaults.toggleableShapes(
                                pressedShape = MaterialTheme.shapes.extraSmall,
                                checkedShape = MaterialTheme.shapes.large,
                            ),
                        ) {
                            Icon(
                                imageVector = if (cat.isFavorite) {
                                    Icons.Filled.Favorite
                                } else {
                                    Icons.Filled.FavoriteBorder
                                },
                                contentDescription = stringResource(
                                    if (cat.isFavorite) {
                                        R.string.cd_remove_favorite
                                    } else {
                                        R.string.cd_add_favorite
                                    },
                                ),
                                tint = if (cat.isFavorite) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                            )
                        }

                        if (isEditMode) {
                            FilledTonalIconButton(
                                onClick = onRequestDelete,
                                colors = IconButtonDefaults.filledTonalIconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error,
                                ),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = stringResource(R.string.cd_delete_cat),
                                )
                            }
                        } else if (!isExpanded) {
                            Text(
                                text = ageLabel,
                                style = MaterialTheme.typography.bodyMediumEmphasized,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }

                        Icon(
                            imageVector = if (isExpanded) {
                                Icons.Filled.KeyboardArrowUp
                            } else {
                                Icons.Filled.KeyboardArrowDown
                            },
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                },
            ) {

                Text(
                    cat.name,
                    style = if (isExpanded) {
                        MaterialTheme.typography.headlineSmallEmphasized
                    } else {
                        MaterialTheme.typography.titleLarge
                    },
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            val expandSpec = MaterialTheme.motionScheme.defaultSpatialSpec<IntSize>()
            val fadeSpec = MaterialTheme.motionScheme.defaultEffectsSpec<Float>()
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = expandSpec) +
                    fadeIn(animationSpec = fadeSpec),
                exit = shrinkVertically(animationSpec = expandSpec) +
                    fadeOut(animationSpec = fadeSpec),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.m),
                ) {
                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = Spacing.m),
                        color = MaterialTheme.colorScheme.outlineVariant,
                    )

                    LabeledDetail(
                        labelRes = R.string.cat_item_age,
                        value = ageLabel,
                    )

                    LabeledDetail(
                        labelRes = R.string.cat_item_breed,
                        value = cat.breed.displayName,
                        modifier = Modifier.padding(top = Spacing.s),
                    )

                    if (cat.image.isNotEmpty()) {
                        var expandedImageAspectRatio by remember { mutableFloatStateOf(1f) }

                        AsyncImage(
                            model = cat.image,
                            contentDescription = stringResource(
                                R.string.cat_photo_description,
                                cat.name,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Spacing.m)
                                .aspectRatio(expandedImageAspectRatio)
                                .heightIn(min = 150.dp, max = 300.dp)
                                .clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop,
                            onSuccess = { success ->
                                val drawable = success.result.drawable
                                expandedImageAspectRatio =
                                    drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight.toFloat()
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LabeledDetail(
    labelRes: Int,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(labelRes),
            style = MaterialTheme.typography.titleMediumEmphasized,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview
@Composable
private fun CatItemPreview() {
    CatbraryTheme {
        CatItem(cat = Cat("Whiskers", 3, CatBreeds.Siamese, ""))
    }
}

@Preview
@Composable
private fun CatItemEditModePreview() {
    CatbraryTheme {
        CatItem(cat = Cat("Whiskers", 3, CatBreeds.Siamese, ""), isEditMode = true)
    }
}
