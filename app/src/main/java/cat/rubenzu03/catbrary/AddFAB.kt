package cat.rubenzu03.catbrary

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun CreateFAB(navController: NavController) {
    ExtendedFloatingActionButton(
        onClick = { navController.navigate("add") },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor   = MaterialTheme.colorScheme.primary,
        icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
        text = { Text("Add Cat") }
    )
}