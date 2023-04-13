package com.aueb.healthmonitor.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalLineSeparator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}