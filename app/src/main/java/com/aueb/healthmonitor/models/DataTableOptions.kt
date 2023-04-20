package com.aueb.healthmonitor.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.ui.graphics.vector.ImageVector
import com.aueb.healthmonitor.ui.components.datatable.DataTableItem

data class DataTableOptions(
    val icon: ImageVector = Icons.Default.BrokenImage,
    val title: String = "Title",
    val records: List<DataTableItem> = listOf()
)
