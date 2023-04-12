package com.aueb.healthmonitor.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Blue,
    contentColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(50),
    padding: Dp = 16.dp,
    elevation: Dp = 8.dp
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .background(color = backgroundColor, shape = shape)
            .padding(padding),
        elevation = ButtonDefaults.elevation(defaultElevation = elevation),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Text(text = text)
    }
}