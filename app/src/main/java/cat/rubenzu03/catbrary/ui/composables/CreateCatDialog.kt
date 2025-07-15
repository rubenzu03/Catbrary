package cat.rubenzu03.catbrary.ui.composables

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.rubenzu03.catbrary.domain.CatBreeds
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModel


@Composable
fun CreateCatFABDialog(onDismiss: () -> Unit,
                       viewModel: CreateCatViewModel = viewModel()
) {

    val name = viewModel.name
    val age = viewModel.age
    val selectedBreed = viewModel.selectedBreed
    val imageUri = viewModel.imageUri

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        { uri: Uri? ->
            viewModel.imageUri = uri
        }
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
                        "Add a new Cat",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                LabeledTextField(
                    value = name,
                    onValueChange = { viewModel.name = it },
                    label = "Cat Name",
                    placeholder = "Enter the name of your cat",
                    modifier = Modifier.fillMaxWidth(),
                )

                LabeledDropDown(
                    options = CatBreeds.entries,
                    selectedOption = selectedBreed,
                    onOptionSelected = { viewModel.selectedBreed = it },
                    label = "Cat Breed",
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Select a breed",
                    optionToString = { it.name.replace('_', ' ').lowercase().replaceFirstChar { it.uppercase() } }
                )

                LabeledTextField(
                    value = age,
                    onValueChange = { viewModel.age = it },
                    label = "Cat Age",
                    placeholder = "Enter the age of your cat",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )


                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SaveFillButton(
                        onClick = {
                            viewModel.saveCat()
                            onDismiss()
                                  },
                        modifier = Modifier.width(200.dp).height(60.dp)
                    )
                }

            }
        }
    }
}
