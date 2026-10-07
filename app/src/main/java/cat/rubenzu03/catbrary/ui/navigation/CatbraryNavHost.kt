package cat.rubenzu03.catbrary.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cat.rubenzu03.catbrary.ui.screens.BreedsScreen
import cat.rubenzu03.catbrary.ui.screens.FavoritesScreen
import cat.rubenzu03.catbrary.ui.screens.HomeScreen
import cat.rubenzu03.catbrary.ui.screens.SearchScreen
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModel

@Composable
fun CatbraryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CreateCatViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    isExpandedWidth: Boolean,
    onAddCat: () -> Unit,
) {
    val motionScheme = MaterialTheme.motionScheme

    val fadeThroughEnter = remember(motionScheme) {
        fadeIn(animationSpec = motionScheme.defaultEffectsSpec()) +
            scaleIn(initialScale = 0.92f, animationSpec = motionScheme.defaultSpatialSpec())
    }
    val fadeThroughExit = remember(motionScheme) {
        fadeOut(animationSpec = motionScheme.fastEffectsSpec())
    }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        enterTransition = { fadeThroughEnter },
        exitTransition = { fadeThroughExit },
        popEnterTransition = { fadeThroughEnter },
        popExitTransition = { fadeThroughExit },
    ) {
        composable(Routes.HOME) {
            HomeScreen(viewModel = viewModel, onAddCat = onAddCat)
        }
        composable(Routes.FAVORITES) {
            FavoritesScreen(viewModel = viewModel)
        }
        composable(Routes.SEARCH) {
            SearchScreen(isExpandedWidth = isExpandedWidth)
        }
        composable(Routes.BREEDS) {
            BreedsScreen()
        }
    }
}
