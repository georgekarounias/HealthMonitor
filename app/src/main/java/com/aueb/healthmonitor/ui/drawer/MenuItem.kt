package com.aueb.healthmonitor.ui.drawer

import androidx.compose.ui.graphics.vector.ImageVector
import com.aueb.healthmonitor.ui.Screen

data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String,
    val screenInfo: Screen,
    val icon: ImageVector
)
