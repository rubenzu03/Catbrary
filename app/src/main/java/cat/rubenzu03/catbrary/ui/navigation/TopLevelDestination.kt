package cat.rubenzu03.catbrary.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import cat.rubenzu03.catbrary.R

object Routes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val BREEDS = "breeds"
    const val FAVORITES = "favorites"
}

data class TopLevelDestination(
    val route: String,
    val icon: ImageVector,
    @field:StringRes val labelRes: Int,
    @field:StringRes val cdRes: Int,
) {
    companion object {
        val ALL = listOf(
            TopLevelDestination(Routes.HOME, Icons.Filled.Home, R.string.home_bottombar, R.string.cd_home),
            TopLevelDestination(Routes.SEARCH, Icons.Filled.Search, R.string.search_bottombar, R.string.cd_search),
            TopLevelDestination(Routes.BREEDS, Icons.Filled.Info, R.string.breeds_topbar, R.string.cd_breeds),
            TopLevelDestination(Routes.FAVORITES, Icons.Filled.Favorite, R.string.favorites_bottombar, R.string.cd_favorites),
        )
    }
}
