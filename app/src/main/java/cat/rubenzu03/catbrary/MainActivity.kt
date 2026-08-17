package cat.rubenzu03.catbrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.ExpandedDockedSearchBar
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.animation.scaleIn
import cat.rubenzu03.catbrary.ui.composables.CatBreedListScreen
import cat.rubenzu03.catbrary.ui.viewmodel.CatBreedListViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.input.nestedscroll.nestedScroll

private sealed interface BreedContent {
    data object Loading : BreedContent
    data class Error(val message: String) : BreedContent
    data object Content : BreedContent
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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
                val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                    canScroll = { currentRoute == "home" || currentRoute == "breeds" }
                )
                LaunchedEffect(currentRoute) {
                    topAppBarScrollBehavior.state.heightOffset = 0f
                    topAppBarScrollBehavior.state.contentOffset = 0f
                }
val windowSizeClass = calculateWindowSizeClass(this@MainActivity)
                if (windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        AppNavigationRail(navController, currentRoute)
                        Scaffold(
                            topBar = { TopApplicationBar(viewModel, currentRoute, topAppBarScrollBehavior) },
                            floatingActionButton = { CreateFAB(viewModel = viewModel) },
                            floatingActionButtonPosition = FabPosition.End
                        ) { innerPadding ->
                            CatbraryNavHost(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding),
                                viewModel = viewModel,
                                scrollBehavior = topAppBarScrollBehavior
                            )
                        }
                    }
                } else {
                    Scaffold(
                        topBar = { TopApplicationBar(viewModel, currentRoute, topAppBarScrollBehavior) },
                        bottomBar = { BottomNavigationBar(navController) },
                        floatingActionButton = { CreateFAB(viewModel = viewModel) },
                        floatingActionButtonPosition = FabPosition.End
                    ) { innerPadding ->
                        CatbraryNavHost(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding),
                            viewModel = viewModel,
                            scrollBehavior = topAppBarScrollBehavior
                        )
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
                icon = { Icon(painterResource(R.drawable.ic_home), contentDescription = "Home") },
                label = { Text(stringResource(R.string.home_bottombar)) }
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
                icon = { Icon(painterResource(R.drawable.ic_search), contentDescription = "Search") },
                label = { Text(stringResource(R.string.search_bottombar)) }
            )

            NavigationBarItem(
                selected = currentRoute == "breeds",
                onClick = {
                    if (currentRoute != "breeds") {
                        navController.navigate("breeds") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(painterResource(R.drawable.ic_info), contentDescription = "Breeds") },
                label = { Text(stringResource(R.string.breeds_bottombar)) }
            )

            NavigationBarItem(
                selected = currentRoute == "favorites",
                onClick = {
                    if (currentRoute != "favorites") {
                        navController.navigate("favorites") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(painterResource(R.drawable.ic_favorite), contentDescription = "Favorites") },
                label = { Text(stringResource(R.string.favorites_bottombar)) }
            )
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
    private fun AppNavigationRail(navController: NavHostController, currentRoute: String?) {
        NavigationRail {
            NavigationRailItem(
                selected = currentRoute == "home",
                onClick = {
                    if (currentRoute != "home") {
                        navController.navigate("home") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(painterResource(R.drawable.ic_home), contentDescription = "Home") },
                label = { Text(stringResource(R.string.home_bottombar)) }
            )

            NavigationRailItem(
                selected = currentRoute == "search",
                onClick = {
                    if (currentRoute != "search") {
                        navController.navigate("search") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(painterResource(R.drawable.ic_search), contentDescription = "Search") },
                label = { Text(stringResource(R.string.search_bottombar)) }
            )

            NavigationRailItem(
                selected = currentRoute == "breeds",
                onClick = {
                    if (currentRoute != "breeds") {
                        navController.navigate("breeds") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(painterResource(R.drawable.ic_info), contentDescription = "Breeds") },
                label = { Text(stringResource(R.string.breeds_bottombar)) }
            )

            NavigationRailItem(
                selected = currentRoute == "favorites",
                onClick = {
                    if (currentRoute != "favorites") {
                        navController.navigate("favorites") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(painterResource(R.drawable.ic_favorite), contentDescription = "Favorites") },
                label = { Text(stringResource(R.string.favorites_bottombar)) }
            )
        }
    }

    @Composable
    private fun CatbraryNavHost(
        navController: NavHostController,
        modifier: Modifier,
        viewModel: CreateCatViewModel,
        scrollBehavior: TopAppBarScrollBehavior
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
            startDestination = "home",
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            enterTransition = { fadeThroughEnter },
            exitTransition = { fadeThroughExit },
            popEnterTransition = { fadeThroughEnter },
            popExitTransition = { fadeThroughExit }
        ) {
            composable("home") { MainScreen(viewModel = viewModel) }
            composable("favorites") { FavoritesScreen(viewModel = viewModel) }
            composable("search") { SearchScreen() }
            composable("breeds") {
                BreedInfoScreen()
            }
        }
    }

    @Composable
    fun BreedInfoScreen() {
        val breedViewModel: CatBreedListViewModel = viewModel()
        val breeds = breedViewModel.breeds.collectAsState().value
        val loading = breedViewModel.loading.collectAsState().value
        val error = breedViewModel.error.collectAsState().value
        LaunchedEffect(Unit) { breedViewModel.fetchBreeds() }
        val target = when {
            loading -> BreedContent.Loading
            error != null -> BreedContent.Error(error)
            else -> BreedContent.Content
        }
        val motionScheme = MaterialTheme.motionScheme
        val transitionSpec = remember(motionScheme) {
            (fadeIn(motionScheme.fastEffectsSpec()) +
                scaleIn(initialScale = 0.92f, animationSpec = motionScheme.fastSpatialSpec()))
                .togetherWith(fadeOut(motionScheme.fastEffectsSpec()))
        }
        AnimatedContent(
            targetState = target,
            transitionSpec = { transitionSpec }
        ) { state ->
            when (state) {
                BreedContent.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is BreedContent.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${state.message}")
                    }
                }

                BreedContent.Content -> CatBreedListScreen(breeds)
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopApplicationBar(
        viewModel: CreateCatViewModel,
        currentRoute: String?,
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        val title = when (currentRoute) {
            "home" -> stringResource(R.string.home_bottombar)
            "search" -> stringResource(R.string.search_topbar)
            "breeds" -> stringResource(R.string.breeds_topbar)
            "favorites" -> stringResource(R.string.favorites_topbar)
            else -> stringResource(R.string.app_name)
        }
        LargeFlexibleTopAppBar(
            title = { Text(title) },
            actions = {
                if (currentRoute == "home") {
                    IconButton(onClick = { viewModel.toggleEditMode() }) {
                        Icon(
                            painter = if (viewModel.isEditMode) painterResource(R.drawable.ic_check) else painterResource(R.drawable.ic_edit),
                            contentDescription = if (viewModel.isEditMode) "Done editing" else "Edit cats"
                        )
                    }
                }
            },
            scrollBehavior = scrollBehavior
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
            onDeleteCat = { cat -> viewModel.deleteCat(cat) },
            onToggleFavorite = { cat -> viewModel.toggleFavorite(cat) }
        )
    }


    @Composable
    fun FavoritesScreen(modifier: Modifier = Modifier, viewModel: CreateCatViewModel) {
        LaunchedEffect(Unit) {
            viewModel.loadFavoriteCats()
        }
        val favoriteCats = viewModel.favoriteCats

        if (favoriteCats.isEmpty()) {
            Box(
                modifier = modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.favorites_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            CatList(
                cats = favoriteCats,
                modifier = modifier,
                onToggleFavorite = { cat -> viewModel.toggleFavorite(cat) }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    fun SearchScreen(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        val repo = remember { CatRepository.getInstance(context) }
        val searchFactory = remember { SearchViewModelFactory(repo) }
        val searchViewModel: SearchViewModel = viewModel(factory = searchFactory)

        val searchResults by searchViewModel.searchResults
        val isSearching by searchViewModel.isSearching
        val scope = rememberCoroutineScope()

        val searchBarState = rememberSearchBarState()
        val textFieldState = rememberTextFieldState()

        LaunchedEffect(Unit) {
            snapshotFlow { textFieldState.text.toString() }
                .collect { query -> searchViewModel.updateSearchQuery(query) }
        }

        val inputField = @Composable {
            SearchBarDefaults.InputField(
                textFieldState = textFieldState,
                searchBarState = searchBarState,
                onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
                placeholder = {
                    Text(
                        stringResource(R.string.search_hint),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                leadingIcon = { Icon(painterResource(R.drawable.ic_search), contentDescription = "Search") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(
                state = searchBarState,
                inputField = inputField,
                modifier = Modifier.fillMaxWidth()
            )
        }

        val expandedContent: @Composable ColumnScope.() -> Unit = {
            when {
                isSearching -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                searchResults.isNotEmpty() -> {
                    CatList(
                        cats = searchResults,
                        modifier = Modifier.padding(top = 16.dp),
                        isEditMode = false,
                        onDeleteCat = {}
                    )
                }

                textFieldState.text.isNotEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.search_no_results) +
                                "\"${textFieldState.text}\""
                        )
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.search_hint))
                    }
                }
            }
        }

        val windowSizeClass = calculateWindowSizeClass(this@MainActivity)
        if (windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium) {
            ExpandedDockedSearchBar(
                state = searchBarState,
                inputField = inputField,
                modifier = Modifier.padding(16.dp),
                content = expandedContent
            )
        } else {
            ExpandedFullScreenSearchBar(
                state = searchBarState,
                inputField = inputField,
                modifier = Modifier.padding(16.dp),
                content = expandedContent
            )
        }
    }
}