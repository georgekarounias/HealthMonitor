package com.aueb.healthmonitor.ui.vitalsscreen.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HealthRecordMenu(items: List<HealthRecordMenuItem>, onSelected: (HealthRecordMenuItem)->Unit, selected: HealthRecordMenuItem? = null) {
    val listState = rememberLazyListState()

    LazyRow(state = listState) {
        items(items) { item ->
            Card(
                modifier = Modifier.padding(8.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp),
                backgroundColor = if(selected == item)  Color.Gray else Color.White,
                onClick = {onSelected(item)}
            ) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(80.dp)
                ) {
                    Text(
                        text = item.name,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}