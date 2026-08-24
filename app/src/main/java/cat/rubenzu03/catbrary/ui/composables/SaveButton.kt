package cat.rubenzu03.catbrary.ui.composables

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cat.rubenzu03.catbrary.R


@Composable
fun SaveFillButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .heightIn(min = ButtonDefaults.MediumContainerHeight),
        enabled = enabled
    ) {
        Text(stringResource(R.string.save_button))
    }
}
