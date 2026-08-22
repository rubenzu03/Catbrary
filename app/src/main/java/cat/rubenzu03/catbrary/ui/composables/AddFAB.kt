package cat.rubenzu03.catbrary.ui.composables

import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.persistence.CatRepository
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModel


@Composable
fun CreateFAB(viewModel: CreateCatViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val haptics = LocalHapticFeedback.current

    val context = LocalContext.current
    val repo = remember { CatRepository.getInstance(context) }

    if (showDialog) {
        CreateCatFABDialog(
            onDismiss = { showDialog = false },
            viewModel = viewModel
        )
    }

    FloatingActionButtonMenu(
        expanded = expanded,
        button = {
            ToggleFloatingActionButton(
                checked = expanded,
                onCheckedChange = {
                    haptics.performHapticFeedback(HapticFeedbackType.ToggleOn)
                    expanded = it
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = stringResource(R.string.cd_add),
                    modifier = Modifier
                        .animateIcon(checkedProgress = { checkedProgress })
                        .graphicsLayer { rotationZ = checkedProgress * 45f }
                )
            }
        }
    ) {
        FloatingActionButtonMenuItem(
            onClick = {
                expanded = false
                showDialog = true
            },
            text = { Text(stringResource(R.string.add_cat)) },
            icon = { Icon(painter = painterResource(R.drawable.ic_add_a_photo), contentDescription = null) }
        )
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