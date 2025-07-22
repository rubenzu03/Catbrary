package cat.rubenzu03.catbrary.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.persistence.CatRepository
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModel


@Composable
fun CreateFAB(viewModel: CreateCatViewModel) {

    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val repo = remember { CatRepository.getInstance(context) }

    if (showDialog) {
        CreateCatFABDialog(
            onDismiss = { showDialog = false },
            viewModel = viewModel
        )
    }

    ExtendedFloatingActionButton(
        onClick = { showDialog = true },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor   = MaterialTheme.colorScheme.primary,
        icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
        text = { Text(stringResource(R.string.add_cat)) }
    )
}

@Preview
@Composable
fun CreateFABPreview() {
    val context = LocalContext.current
    val repo = remember { CatRepository.getInstance(context) }
    val viewModel: CreateCatViewModel = viewModel { CreateCatViewModel(repo, context) }
    CreateFAB(viewModel = viewModel)
}