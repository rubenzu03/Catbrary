package cat.rubenzu03.catbrary.ui.composables

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.domain.CatBreeds
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModel
import coil.compose.AsyncImage

import androidx.compose.material3.*


@Composable
fun CreateCatFABDialog(onDismiss: () -> Unit,
                       viewModel: CreateCatViewModel
) {
    val name = viewModel.name
    val age = viewModel.age
    val selectedBreed = viewModel.selectedBreed
    val imageUri = viewModel.imageUri

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.imageUri = uri
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(12.dp)
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .heightIn(max = 600.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                    Text(
                        stringResource(R.string.cat_dialog_title),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                LabeledTextField(
                    value = name,
                    onValueChange = { viewModel.name = it },
                    label = stringResource(R.string.cat_dialog_name_label),
                    placeholder = stringResource(R.string.cat_dialog_name_hint),
                    modifier = Modifier.fillMaxWidth(),
                )

                LabeledDropDown(
                    options = CatBreeds.entries,
                    selectedOption = selectedBreed,
                    onOptionSelected = { viewModel.selectedBreed = it },
                    label = stringResource(R.string.cat_dialog_breed_label),
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = stringResource(R.string.cat_dialog_name_label),
                    optionToString = { it.name.replace('_', ' ').lowercase().replaceFirstChar { it.uppercase() } }
                )

                LabeledTextField(
                    value = age,
                    onValueChange = { viewModel.age = it },
                    label = stringResource(R.string.cat_dialog_age_label),
                    placeholder = stringResource(R.string.cat_dialog_age_hint),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    stringResource(R.string.cat_dialog_cat_photo_text),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .let { modifier ->
                            if (imageUri != null) {
                                modifier.wrapContentHeight()
                            } else {
                                modifier.height(200.dp)
                            }
                        }
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Cat Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 150.dp, max = 400.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AddAPhoto,
                                contentDescription = "Add Photo",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                stringResource(R.string.cat_dialog_cat_photo_hint),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    FloatingActionButton(
                        onClick = {
                            imagePickerLauncher.launch("image/*")
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp),
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Image"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (viewModel.errorMessage != null) {
                    Text(
                        text = viewModel.errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SaveFillButton(
                        onClick = {
                            if (viewModel.saveCat()) {
                                onDismiss()
                            }
                        },
                        modifier = Modifier.width(200.dp).height(60.dp),
                        enabled = viewModel.isValid()
                    )
                }

            }
        }
    }
}
