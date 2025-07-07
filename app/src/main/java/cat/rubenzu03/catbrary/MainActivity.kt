package cat.rubenzu03.catbrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon;
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

var selectedScreen by mutableStateOf(Screen.Home)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatbraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar =  { BottomNavigationBar()},
                    topBar = { TopApplicationBar() }
                ) { innerPadding ->
                    when (selectedScreen) {
                        Screen.Home -> MainScreen(Modifier.padding(innerPadding))
                        Screen.Add -> AddCatForm(Modifier.padding(innerPadding))
                        Screen.Favorites -> FavoritesScreen(Modifier.padding(innerPadding))
                        Screen.Search -> SearchScreen(Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}



@Preview
@Composable
fun BottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            selected = (selectedScreen == Screen.Home),
            onClick = { selectedScreen = Screen.Home },
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
            selected = (selectedScreen == Screen.Add),
            onClick = { selectedScreen = Screen.Add },
            icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
            label = { Text("Add") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )
        NavigationBarItem(
            selected = (selectedScreen == Screen.Favorites),
            onClick =  { selectedScreen = Screen.Favorites },
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
            selected = (selectedScreen == Screen.Search),
            onClick = { selectedScreen = Screen.Search },
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopApplicationBar(){
    TopAppBar(
        title = { Text("Catbrary") },
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
fun MainScreen(modifier: Modifier = Modifier) {
    Text(
        text = "Welcome to Catbrary!",
        /* TYPOGRAPHY TEST style = MaterialTheme.typography.headlineLarge, */
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun AddCatForm(modifier: Modifier = Modifier) {
    Text(
        text = "Add Cat Form",
        modifier = modifier.padding(16.dp)
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

enum class Screen {
    Home,
    Add,
    Favorites,
    Search
}