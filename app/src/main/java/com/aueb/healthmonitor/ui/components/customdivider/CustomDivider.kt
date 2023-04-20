package com.aueb.healthmonitor.ui.components.customdivider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray),
        thickness = 1.dp
    )
}