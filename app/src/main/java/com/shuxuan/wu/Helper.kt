package com.shuxuan.wu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

//Student ID: 301203269
//Student Name: Shuxuan Wu
//Helper Functions for CheckBox And RadioButton
@Composable
fun CheckboxGroup(
    options: List<String>,
    selectedOptions: Set<String>,
    onCheckedChange: (String, Boolean) -> Unit
) {
    Column {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCheckedChange(option, !selectedOptions.contains(option)) }
            ) {
                Checkbox(
                    checked = selectedOptions.contains(option),
                    onCheckedChange = { isChecked ->
                        onCheckedChange(option, isChecked)
                    }
                )
                Text(text = option)
            }
        }
    }
}

@Composable
fun RadioButtonGroup(options: List<String>, onSelectedChange: (String) -> Unit) {
    var selectedOption by remember { mutableStateOf("") }

    Column {
        options.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = {
                        selectedOption = option
                        onSelectedChange(option)
                    }
                )
                Text(text = option)
            }
        }
    }
}
