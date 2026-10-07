package cat.rubenzu03.catbrary.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cat.rubenzu03.catbrary.R

val ConnectedLeadingButtonShapes: ButtonShapes
    @Composable get() = ButtonShapes(
        shape = ButtonGroupDefaults.connectedLeadingButtonShape,
        pressedShape = ButtonGroupDefaults.connectedLeadingButtonPressShape,
    )

val ConnectedTrailingButtonShapes: ButtonShapes
    @Composable get() = ButtonShapes(
        shape = ButtonGroupDefaults.connectedTrailingButtonShape,
        pressedShape = ButtonGroupDefaults.connectedTrailingButtonPressShape,
    )

@Composable
fun ConnectedClearButton(
    onClick: () -> Unit,
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    FilledTonalButton(
        onClick = onClick,
        enabled = enabled,
        shapes = ConnectedLeadingButtonShapes,
        modifier = modifier.heightIn(min = ButtonDefaults.MediumContainerHeight),
    ) {
        Text(stringResource(labelRes))
    }
}

@Composable
fun ConnectedSaveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shapes = ConnectedTrailingButtonShapes,
        modifier = modifier.heightIn(min = ButtonDefaults.MediumContainerHeight),
    ) {
        Text(stringResource(R.string.save_button))
    }
}
