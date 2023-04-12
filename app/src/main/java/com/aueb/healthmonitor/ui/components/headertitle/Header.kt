package com.aueb.healthmonitor.ui.components.headertitle

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit


@Composable
fun Header(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.typography.h4.fontSize,
    color: Color = MaterialTheme.colors.onSurface,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    fontFeatureSettings: String? = null,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.h4.copy(
            fontSize = fontSize,
            color = color,
            textAlign = textAlign,
            textDecoration = textDecoration,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight,
            fontFeatureSettings = fontFeatureSettings
        )
    )
}