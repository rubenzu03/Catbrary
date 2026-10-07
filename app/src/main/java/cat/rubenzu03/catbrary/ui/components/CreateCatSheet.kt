package cat.rubenzu03.catbrary.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.domain.CatBreeds
import cat.rubenzu03.catbrary.ui.theme.CatbraryTheme
import cat.rubenzu03.catbrary.ui.theme.Spacing
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCatSheet(
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onClear: () -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    age: String,
    onAgeChange: (String) -> Unit,
    selectedBreed: CatBreeds?,
    onBreedSelected: (CatBreeds) -> Unit,
    imageUri: Uri?,
    onImagePicked: (Uri?) -> Unit,
    errorMessageResId: Int?,
    canSave: Boolean,
) {

    val sheetState = rememberBottomSheetState(
        initialValue = SheetValue.Expanded,
        enabledValues = setOf(SheetValue.Hidden, SheetValue.Expanded),
    )

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? -> onImagePicked(uri) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.l)
                .padding(bottom = Spacing.l),
        ) {

            Text(
                stringResource(R.string.cat_dialog_title),
                style = MaterialTheme.typography.headlineMediumEmphasized,
                color = MaterialTheme.colorScheme.onSurface,
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = Spacing.m))

            LabeledTextField(
                value = name,
                onValueChange = onNameChange,
                label = stringResource(R.string.cat_dialog_name_label),
                placeholder = stringResource(R.string.cat_dialog_name_hint),
                modifier = Modifier.fillMaxWidth(),
            )

            LabeledDropDown(
                options = CatBreeds.entries,
                selectedOption = selectedBreed,
                onOptionSelected = onBreedSelected,
                label = stringResource(R.string.cat_dialog_breed_label),
                modifier = Modifier.fillMaxWidth(),
                placeholder = stringResource(R.string.cat_dialog_breed_hint),
                optionToString = { it.displayName },
            )

            LabeledTextField(
                value = age,
                onValueChange = onAgeChange,
                label = stringResource(R.string.cat_dialog_age_label),
                placeholder = stringResource(R.string.cat_dialog_age_hint),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )

            Text(
                stringResource(R.string.cat_dialog_cat_photo_text),
                style = MaterialTheme.typography.titleMediumEmphasized,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = Spacing.m, bottom = Spacing.xs),
            )

            PhotoPickerBox(
                imageUri = imageUri,
                onPickImage = { imagePickerLauncher.launch("image/*") },
            )

            if (errorMessageResId != null) {
                Text(
                    text = stringResource(id = errorMessageResId),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = Spacing.s),
                )
            }

            ButtonGroup(
                overflowIndicator = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing.l),

                expandedRatio = 0f,
            ) {

                val halfWidth = Modifier.weight(1f)

                customItem(
                    buttonGroupContent = {
                        ConnectedClearButton(
                            onClick = onClear,
                            labelRes = R.string.clear_button,
                            modifier = halfWidth,
                            enabled = canSave,
                        )
                    },
                    menuContent = {},
                )
                customItem(
                    buttonGroupContent = {
                        ConnectedSaveButton(
                            onClick = onSave,
                            modifier = halfWidth,
                            enabled = canSave,
                        )
                    },
                    menuContent = {},
                )
            }
        }
    }
}

@Composable
private fun PhotoPickerBox(imageUri: Uri?, onPickImage: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                MaterialTheme.shapes.medium,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = stringResource(R.string.cd_cat_photo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp)
                    .clip(MaterialTheme.shapes.medium),
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(Spacing.l),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add_a_photo),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(48.dp),
                )
                Text(
                    text = stringResource(R.string.cat_dialog_cat_photo_hint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = Spacing.xs),
                )
            }
        }

        FilledTonalIconButton(
            onClick = onPickImage,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Spacing.s),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_add_a_photo),
                contentDescription = stringResource(R.string.cd_add_image),
            )
        }
    }
}

@Preview
@Composable
private fun CreateCatSheetPreview() {
    CatbraryTheme {
        Box(modifier = Modifier.height(600.dp)) {
            CreateCatSheet(
                onDismiss = {},
                onSave = {},
                onClear = {},
                name = "",
                onNameChange = {},
                age = "",
                onAgeChange = {},
                selectedBreed = null,
                onBreedSelected = {},
                imageUri = null,
                onImagePicked = {},
                errorMessageResId = null,
                canSave = false,
            )
        }
    }
}
