package cat.rubenzu03.catbrary.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cat.rubenzu03.catbrary.R
import cat.rubenzu03.catbrary.ui.theme.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatbraryTopAppBar(
    currentRoute: String?,
    isEditMode: Boolean,
    onToggleEditMode: () -> Unit,
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    if (currentRoute == Routes.SEARCH) return

    val title = when (currentRoute) {
        Routes.HOME -> stringResource(R.string.home_bottombar)
        Routes.BREEDS -> stringResource(R.string.breeds_topbar)
        Routes.FAVORITES -> stringResource(R.string.favorites_topbar)
        else -> stringResource(R.string.app_name)
    }

    val actions: @Composable RowScope.() -> Unit = {
        if (currentRoute == Routes.HOME) {

            FilledTonalIconToggleButton(
                checked = isEditMode,
                onCheckedChange = { onToggleEditMode() },
                shapes = IconButtonDefaults.toggleableShapes(
                    pressedShape = MaterialTheme.shapes.extraSmall,
                    checkedShape = MaterialTheme.shapes.large,
                ),
            ) {
                Icon(
                    imageVector = if (isEditMode) {
                        Icons.Filled.Check
                    } else {
                        Icons.Filled.Edit
                    },
                    contentDescription = stringResource(
                        if (isEditMode) R.string.cd_done_editing else R.string.cd_edit,
                    ),
                )
            }
        }
        ThemeModeMenu(
            currentMode = themeMode,
            onModeChange = onThemeModeChange,
        )
    }

    if (currentRoute == Routes.HOME) {
        LargeFlexibleTopAppBar(
            title = { Text(title) },
            actions = actions,
            scrollBehavior = scrollBehavior,
            modifier = modifier,
        )
    } else {
        MediumTopAppBar(
            title = { Text(title) },
            actions = actions,
            scrollBehavior = scrollBehavior,
            modifier = modifier,
        )
    }
}
