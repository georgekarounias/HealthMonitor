package com.aueb.healthmonitor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender

@Composable
fun DropDownMenu(
    items: List<AdministrativeGender>,
    onItemSelected: (AdministrativeGender) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items[0].toCode()) }

    Column(modifier = modifier.wrapContentSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .background(Color.LightGray)
        ) {
            Text(
                text = selectedItem,
                modifier = Modifier.padding(16.dp),
                color = Color.Black
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "DropDown Arrow",
                modifier = Modifier.align(alignment = Alignment.CenterEnd)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedItem = item.toCode()
                    onItemSelected(item)
                    expanded = false
                }) {
                    Text(
                        text = item.toCode(),
                        modifier = Modifier.padding(16.dp),
                        color = Color.Black
                    )
                }
            }
        }
    }
}