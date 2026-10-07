package cat.rubenzu03.catbrary.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.ui.theme.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeModeMenu(
    currentMode: ThemeMode,
    onModeChange: (ThemeMode) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val tooltipState = rememberTooltipState()

    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = TooltipAnchorPosition.Above,
        ),
        tooltip = { PlainTooltip { Text(stringResource(R.string.theme)) } },
        state = tooltipState,
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                painter = painterResource(R.drawable.ic_theme),
                contentDescription = stringResource(R.string.theme),
            )
        }
    }

    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        ThemeMode.entries.forEach { mode ->
            val selected = mode == currentMode
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(mode.labelRes),
                        style = if (selected) {
                            MaterialTheme.typography.titleMediumEmphasized
                        } else {
                            MaterialTheme.typography.bodyLarge
                        },
                    )
                },

                leadingIcon = if (selected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                        )
                    }
                } else {
                    null
                },
                onClick = {
                    onModeChange(mode)
                    expanded = false
                },
            )
        }
    }
}

private val ThemeMode.labelRes: Int
    get() = when (this) {
        ThemeMode.SYSTEM -> R.string.theme_system
        ThemeMode.LIGHT -> R.string.theme_light
        ThemeMode.DARK -> R.string.theme_dark
        ThemeMode.DYNAMIC -> R.string.theme_dynamic
    }
