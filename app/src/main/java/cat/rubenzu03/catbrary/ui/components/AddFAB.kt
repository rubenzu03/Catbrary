package cat.rubenzu03.catbrary.ui.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.ui.theme.CatbraryTheme

@Composable
fun CreateFAB(onClick: () -> Unit) {
    val haptics = LocalHapticFeedback.current

    FloatingActionButton(onClick = {
        haptics.performHapticFeedback(HapticFeedbackType.ToggleOn)
        onClick()
    }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.cd_add),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Preview
@Composable
private fun CreateFABPreview() {
    CatbraryTheme {
        CreateFAB(onClick = {})
    }
}
