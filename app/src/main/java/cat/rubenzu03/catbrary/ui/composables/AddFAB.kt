package cat.rubenzu03.catbrary.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.persistence.CatRepository
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModel


@Composable
fun CreateFAB(viewModel: CreateCatViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val repo = remember { CatRepository.getInstance(context) }

    if (showDialog) {
        CreateCatFABDialog(
            onDismiss = { showDialog = false },
            viewModel = viewModel
        )
    }

    HorizontalFloatingToolbar(
        expanded = expanded,
        colors = FloatingToolbarDefaults.vibrantFloatingToolbarColors(),
        floatingActionButton = {
            FloatingToolbarDefaults.VibrantFloatingActionButton(
                onClick = { expanded = !expanded }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = "Add"
                )
            }
        }
    ) {
        FilledTonalButton(
            onClick = {
                expanded = false
                showDialog = true
            }
        ) {
            Icon(painter = painterResource(R.drawable.ic_add), contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.add_cat))
        }
    }
}

@Preview
@Composable
fun CreateFABPreview() {
    val context = LocalContext.current
    val repo = remember { CatRepository.getInstance(context) }
    val viewModel: CreateCatViewModel = viewModel { CreateCatViewModel(repo, context) }
    CreateFAB(viewModel = viewModel)
}