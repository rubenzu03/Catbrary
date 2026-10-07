package cat.rubenzu03.catbrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cat.rubenzu03.catbrary.persistence.CatRepository
import cat.rubenzu03.catbrary.ui.components.CreateCatSheet
import cat.rubenzu03.catbrary.ui.components.CreateFAB
import cat.rubenzu03.catbrary.ui.navigation.CatbraryNavHost
import cat.rubenzu03.catbrary.ui.navigation.CatbraryTopAppBar
import cat.rubenzu03.catbrary.ui.navigation.NavigationBarContent
import cat.rubenzu03.catbrary.ui.navigation.Routes
import cat.rubenzu03.catbrary.ui.navigation.ShortNavigationBarContent
import cat.rubenzu03.catbrary.ui.navigation.WideNavigationRailContent
import cat.rubenzu03.catbrary.ui.theme.CatbraryTheme
import cat.rubenzu03.catbrary.ui.theme.LocalThemeMode
import cat.rubenzu03.catbrary.ui.theme.ThemeMode
import cat.rubenzu03.catbrary.ui.viewmodel.CatUiEvent
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModel
import cat.rubenzu03.catbrary.ui.viewmodel.CreateCatViewModelFactory

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val repo = remember { CatRepository.getInstance(context) }
            val factory = remember { CreateCatViewModelFactory(repo, application) }
            val viewModel: CreateCatViewModel = viewModel(factory = factory)

            var themeMode by rememberSaveable(stateSaver = ThemeModeSaver) {
                mutableStateOf(ThemeMode.load(context))
            }

            val widthSizeClass = calculateWindowSizeClass(this@MainActivity).widthSizeClass

            CatbraryTheme(themeMode = themeMode) {
                CatbraryApp(
                    viewModel = viewModel,
                    widthSizeClass = widthSizeClass,
                    onThemeModeChange = { mode ->
                        themeMode = mode
                        ThemeMode.save(context, mode)
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun CatbraryApp(
    viewModel: CreateCatViewModel,
    widthSizeClass: WindowWidthSizeClass,
    onThemeModeChange: (ThemeMode) -> Unit,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val snackbarHostState = remember { SnackbarHostState() }

    val isMediumWidth = widthSizeClass >= WindowWidthSizeClass.Medium
    val isExpandedWidth = widthSizeClass >= WindowWidthSizeClass.Expanded

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        canScroll = { currentRoute == Routes.HOME || currentRoute == Routes.BREEDS },
    )

    LaunchedEffect(currentRoute) {
        scrollBehavior.state.heightOffset = 0f
        scrollBehavior.state.contentOffset = 0f
    }

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is CatUiEvent.Message -> snackbarHostState.showSnackbar(event.text)
            }
        }
    }

    var showCreateSheet by rememberSaveable { mutableStateOf(false) }

    val navigateTo: (String) -> Unit = { route ->
        if (currentRoute != route) {
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    val openSheet = { showCreateSheet = true }

    val scaffold: @Composable (Modifier) -> Unit = { contentModifier ->
        Scaffold(
            topBar = {
                CatbraryTopAppBar(
                    currentRoute = currentRoute,
                    isEditMode = viewModel.isEditMode,
                    onToggleEditMode = viewModel::toggleEditMode,
                    themeMode = LocalThemeMode.current,
                    onThemeModeChange = onThemeModeChange,
                    scrollBehavior = scrollBehavior,
                )
            },
            bottomBar = {
                if (!isExpandedWidth) {
                    if (isMediumWidth) {
                        ShortNavigationBarContent(
                            currentRoute = currentRoute,
                            onNavigate = navigateTo,
                        )
                    } else {
                        NavigationBarContent(
                            currentRoute = currentRoute,
                            onNavigate = navigateTo,
                        )
                    }
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButton = { CreateFAB(onClick = openSheet) },
            floatingActionButtonPosition = FabPosition.End,
        ) { innerPadding ->
            CatbraryNavHost(
                navController = navController,
                modifier = contentModifier.padding(innerPadding),
                viewModel = viewModel,
                scrollBehavior = scrollBehavior,
                isExpandedWidth = isExpandedWidth,
                onAddCat = openSheet,
            )
        }
    }

    if (isExpandedWidth) {
        Row(modifier = Modifier.fillMaxSize()) {
            WideNavigationRailContent(
                currentRoute = currentRoute,
                onNavigate = navigateTo,
            )
            scaffold(Modifier)
        }
    } else {
        scaffold(Modifier)
    }

    if (showCreateSheet) {

        LaunchedEffect(Unit) { viewModel.clearFields() }

        CreateCatSheet(
            onDismiss = { showCreateSheet = false },
            onSave = {
                if (viewModel.saveCat()) showCreateSheet = false
            },
            onClear = viewModel::clearFields,
            name = viewModel.name,
            onNameChange = { viewModel.name = it },
            age = viewModel.age,
            onAgeChange = { viewModel.age = it },
            selectedBreed = viewModel.selectedBreed,
            onBreedSelected = { viewModel.selectedBreed = it },
            imageUri = viewModel.imageUri,
            onImagePicked = { viewModel.imageUri = it },
            errorMessageResId = viewModel.errorMessageResId,
            canSave = viewModel.canSave,
        )
    }
}

private val ThemeModeSaver = Saver<ThemeMode, String>(
    save = { it.name },
    restore = { saved -> ThemeMode.entries.firstOrNull { it.name == saved } ?: ThemeMode.SYSTEM },
)
