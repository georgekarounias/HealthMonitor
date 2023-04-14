package com.aueb.healthmonitor.ui.components.loader


import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex


@Composable
fun LoadingDialog(isLoading: Boolean, title: String = "Loading", text: String = "Please wait...") {
    if (isLoading) {
        Surface(
            color = Color.Gray.copy(alpha = 0.7f),
            modifier = Modifier.fillMaxSize()
                .zIndex(99f)
        ) {
            AlertDialog(
                onDismissRequest = { },
                title = {
                    Text(text = title)
                },
                text = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = text)
                    }
                },
                confirmButton = {},
                dismissButton = {}
            )
        }

    }
}