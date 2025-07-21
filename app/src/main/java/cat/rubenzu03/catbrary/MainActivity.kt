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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cat.rubenzu03.catbrary.persistence.CatRepository
import cat.rubenzu03.catbrary.ui.composables.CatList
import cat.rubenzu03.catbrary.ui.composables.CreateFAB
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModel
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModelFactory
import cat.rubenzu03.catbrary.ui.viewmodel.SearchViewModel
import cat.rubenzu03.catbrary.ui.viewmodel.SearchViewModelFactory
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.CubicBezierEasing

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val repo = remember { CatRepository.getInstance(context) }
            val factory = remember { CreateCatViewModelFactory(repo, context) }
            val viewModel: CreateCatViewModel = viewModel(factory = factory)
            CatbraryTheme {
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                Scaffold(
                    topBar = { TopApplicationBar(viewModel,currentRoute) },
                    bottomBar = { BottomNavigationBar(navController) },
                    floatingActionButton = { CreateFAB(viewModel = viewModel )},
                    floatingActionButtonPosition = FabPosition.End
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding),
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { fullWidth -> fullWidth / 4 },
                                animationSpec = tween(
                                    durationMillis = 320,
                                    easing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 320,
                                    easing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
                                )
                            )
                        },
                        exitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { fullWidth -> -fullWidth / 8 },
                                animationSpec = tween(
                                    durationMillis = 280,
                                    easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 280,
                                    easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
                                )
                            )
                        },
                        popEnterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { fullWidth -> -fullWidth / 8 },
                                animationSpec = tween(
                                    durationMillis = 320,
                                    easing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 320,
                                    easing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { fullWidth -> fullWidth / 4 },
                                animationSpec = tween(
                                    durationMillis = 280,
                                    easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 280,
                                    easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
                                )
                            )
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
                    if (currentRoute != "home") {
                        navController.navigate("home") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
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

            NavigationBarItem(
                selected = currentRoute == "search",
                onClick = {
                    if (currentRoute != "search") {
                        navController.navigate("search") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
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
            )

            /*NavigationBarItem(
                selected = currentRoute == "favorites",
                onClick = {
                    if (currentRoute != "favorites") {
                        navController.navigate("favorites") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
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
            )*/
        }
    }

    @Preview
    @Composable
    fun BottomNavigationBarPreview() {
        val navController = rememberNavController()
        BottomNavigationBar(navController)
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopApplicationBar(viewModel: CreateCatViewModel, currentRoute: String?) {
        val title = when (currentRoute) {
            "home" -> "Home"
            "search" -> "Search Cats"
            "favorites" -> "Favorites"
            else -> "Catbrary"
        }
        TopAppBar(
            title = { Text(title) },
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
        val context = LocalContext.current
        val repo = remember { CatRepository.getInstance(context) }
        val searchFactory = remember { SearchViewModelFactory(repo) }
        val searchViewModel: SearchViewModel = viewModel(factory = searchFactory)

        val searchQuery by searchViewModel.searchQuery
        val searchResults by searchViewModel.searchResults
        val isSearching by searchViewModel.isSearching

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { newQuery -> searchViewModel.updateSearchQuery(newQuery) },
                label = { Text("Search cats by name...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier.fillMaxWidth()
            )

            if (isSearching) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (searchQuery.isNotBlank() && searchResults.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No cats found matching \"$searchQuery\"")
                }
            } else if (searchResults.isNotEmpty()) {
                CatList(
                    cats = searchResults,
                    modifier = Modifier.padding(top = 16.dp),
                    isEditMode = false,
                    onDeleteCat = {}
                )
            } else if (searchQuery.isBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Enter a cat name to search")
                }
            }
        }
    }
}