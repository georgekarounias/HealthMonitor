package com.aueb.healthmonitor.ui.components.datatable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun <T> ForEach(items: List<T>, content: @Composable (T) -> Unit) {
    items.forEach { item ->
        content(item)
    }
}

@Composable
fun<T> DataTable(
    headers: List<String>,
    rows: List<T>,
    //cellContent: @Composable (T, Int) -> Unit
) {
    val tableBackgroundColor = Color.White
    val headerTextColor = Color.Black
    val rowTextColor = Color.DarkGray

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(tableBackgroundColor)
            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
            .padding(16.dp)
    ) {
        // Header row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            headers.forEach { header ->
                Text(
                    text = header,
                    fontWeight = FontWeight.Bold,
                    color = headerTextColor,
                    fontSize = 18.sp
                )
            }
        }

        // Rows
//        LazyColumn(
//            modifier = Modifier.padding(top = 8.dp)
//        ) {
//            var index = 0
//            rows.forEach{row ->
//                index++
//                item{
//                    LazyRow(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ){
//                        item {
//                            //cellContent(row, index)
//                        }
//                    }
//                }
//            }
//        }

    }
}