package cat.rubenzu03.catbrary.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.WideNavigationRail
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cat.rubenzu03.catbrary.ui.theme.CatbraryTheme

@Composable
fun NavigationBarContent(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptics = LocalHapticFeedback.current

    NavigationBar(modifier = modifier.fillMaxWidth()) {
        TopLevelDestination.ALL.forEach { destination ->
            val selected = currentRoute == destination.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        haptics.performHapticFeedback(HapticFeedbackType.ToggleOn)
                        onNavigate(destination.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(destination.cdRes),
                    )
                },
                label = { Text(stringResource(destination.labelRes)) },

                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(),
            )
        }
    }
}

@Composable
fun ShortNavigationBarContent(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptics = LocalHapticFeedback.current

    ShortNavigationBar(modifier = modifier.fillMaxWidth()) {
        TopLevelDestination.ALL.forEach { destination ->
            val selected = currentRoute == destination.route
            ShortNavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        haptics.performHapticFeedback(HapticFeedbackType.ToggleOn)
                        onNavigate(destination.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(destination.cdRes),
                    )
                },
                label = { Text(stringResource(destination.labelRes)) },
            )
        }
    }
}

@Composable
fun WideNavigationRailContent(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptics = LocalHapticFeedback.current
    val state = rememberWideNavigationRailState()

    val railExpanded = state.targetValue == WideNavigationRailValue.Expanded

    WideNavigationRail(state = state, modifier = modifier) {
        TopLevelDestination.ALL.forEach { destination ->
            val selected = currentRoute == destination.route
            WideNavigationRailItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        haptics.performHapticFeedback(HapticFeedbackType.ToggleOn)
                        onNavigate(destination.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(destination.cdRes),
                    )
                },
                label = { Text(stringResource(destination.labelRes)) },
                railExpanded = railExpanded,
            )
        }
    }
}

@Preview
@Composable
private fun NavigationBarContentPreview() {
    CatbraryTheme(themeMode = cat.rubenzu03.catbrary.ui.theme.ThemeMode.LIGHT) {
        NavigationBarContent(currentRoute = Routes.HOME, onNavigate = {})
    }
}

@Preview
@Composable
private fun ShortNavigationBarContentPreview() {
    CatbraryTheme(themeMode = cat.rubenzu03.catbrary.ui.theme.ThemeMode.LIGHT) {
        ShortNavigationBarContent(currentRoute = Routes.BREEDS, onNavigate = {})
    }
}

@Preview
@Composable
private fun WideNavigationRailContentPreview() {
    CatbraryTheme(themeMode = cat.rubenzu03.catbrary.ui.theme.ThemeMode.LIGHT) {
        WideNavigationRailContent(currentRoute = Routes.FAVORITES, onNavigate = {})
    }
}
