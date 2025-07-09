package cat.rubenzu03.catbrary

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController


@Composable
fun CreateFAB() {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        CreateCatFABDialog(onDismiss = { showDialog = false })
    }

    ExtendedFloatingActionButton(
        onClick = { showDialog = true },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor   = MaterialTheme.colorScheme.primary,
        icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
        text = { Text("Add Cat") }
    )
}