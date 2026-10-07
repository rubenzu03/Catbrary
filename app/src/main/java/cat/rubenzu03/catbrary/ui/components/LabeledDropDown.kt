package cat.rubenzu03.catbrary.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cat.rubenzu03.catbrary.ui.theme.CatbraryTheme

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun <T> LabeledDropDown(
    options: List<T>,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    optionToString: (T) -> String = { it.toString() },
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,

        onExpandedChange = { expanded = it },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selectedOption?.let(optionToString) ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            placeholder = if (placeholder != null) {
                { Text(placeholder) }
            } else {
                null
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier

                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                .fillMaxWidth(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(optionToString(option)) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun LabeledDropDownPreview() {
    val options = listOf("Option 1", "Option 2", "Option 3")
    CatbraryTheme {
        LabeledDropDown(
            options = options,
            selectedOption = null,
            onOptionSelected = {},
            label = "Select an option",
            placeholder = "Choose one",
        )
    }
}
