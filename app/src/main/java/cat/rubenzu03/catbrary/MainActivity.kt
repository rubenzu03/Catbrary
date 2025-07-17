package cat.rubenzu03.catbrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cat.rubenzu03.catbrary.ui.theme.CatbraryTheme

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cat.rubenzu03.catbrary.domain.Cat
import cat.rubenzu03.catbrary.domain.CatBreeds
import cat.rubenzu03.catbrary.persistence.CatRepository
import cat.rubenzu03.catbrary.ui.composables.CatList
import cat.rubenzu03.catbrary.ui.composables.CreateFAB
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModel
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModelFactory




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val repo = remember { CatRepository.getInstance(context) }
            val factory = remember { CreateCatViewModelFactory(repo) }
            val viewModel: CreateCatViewModel = viewModel(factory = factory)
            CatbraryTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopApplicationBar(viewModel) },
                    bottomBar = { BottomNavigationBar(navController) },
                    floatingActionButton = { CreateFAB(viewModel = viewModel )},
                    floatingActionButtonPosition = FabPosition.End
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding),

                        enterTransition = {
                            fadeIn(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
                        },
                        exitTransition = {
                            fadeOut(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
                        },
                        popEnterTransition = {
                            fadeIn(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
                        },
                        popExitTransition = {
                            fadeOut(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
                        }

                    ) {
                        composable("home") { MainScreen(viewModel = viewModel) }
                        composable("favorites") { FavoritesScreen() }
                        composable("search") { SearchScreen() }
                    }
                }
            }
        }
    }


    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        val backStack by navController.currentBackStackEntryAsState()
        val currentRoute = backStack?.destination?.route

        NavigationBar {
            NavigationBarItem(
                selected = currentRoute == "home",
                onClick = {
                    navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )

            /*NavigationBarItem(
                selected = currentRoute == "favorites",
                onClick = {
                    navController.navigate("favorites") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorites") },
                label = { Text("Favorites") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
            NavigationBarItem(
                selected = currentRoute == "search",
                onClick = {
                    navController.navigate("search") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                label = { Text("Search") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )*/
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopApplicationBar(viewModel: CreateCatViewModel) {
        TopAppBar(
            title = { Text("Catbrary") },
            actions = {
                IconButton(onClick = { viewModel.toggleEditMode() }) {
                    Icon(
                        imageVector = if (viewModel.isEditMode) Icons.Default.Done else Icons.Default.Edit,
                        contentDescription = if (viewModel.isEditMode) "Done editing" else "Edit cats"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            /*TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),*/
        )
    }


    @Composable
    fun MainScreen(modifier: Modifier = Modifier,
                   viewModel: CreateCatViewModel
    ) {
        LaunchedEffect(Unit) {
            viewModel.loadAllCats()
        }
        val cats = viewModel.cats
        CatList(
            cats = cats,
            modifier = modifier,
            isEditMode = viewModel.isEditMode,
            onDeleteCat = { cat -> viewModel.deleteCat(cat) }
        )
    }


    @Composable
    fun FavoritesScreen(modifier: Modifier = Modifier) {
        Text(
            text = "Favorites Screen",
            modifier = modifier.padding(16.dp)
        )
    }

    @Composable
    fun SearchScreen(modifier: Modifier = Modifier) {
        Text(
            text = "Search Screen",
            modifier = modifier.padding(16.dp)
        )
    }
}