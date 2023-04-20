package com.aueb.healthmonitor.ui.components.datatable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aueb.healthmonitor.models.DataTableOptions
import com.aueb.healthmonitor.ui.components.messagebox.MessageBox

@Composable
fun DataTable(
    options: DataTableOptions
) {
    val tableBackgroundColor = Color.White
    val headerTextColor = Color.Black

    if(options.records.isEmpty()){
        MessageBox(message = "The are 0 records for this period.")
        return
    }

    val rowCount = options.records.count()

    Column(modifier = Modifier
        .padding(16.dp)
        .background(tableBackgroundColor)
        .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
        .padding(16.dp))
    {

        // Header row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = options.title + "(${options.records.size})",
                fontWeight = FontWeight.Bold,
                color = headerTextColor,
                fontSize = 18.sp
            )
        }
        Box(modifier = Modifier.height(300.dp)) {
            LazyColumn {
                items(rowCount) { index ->
                    val currentRow = options.records[index]
                    DataTableItemView(options.icon, currentRow.value, currentRow.date)
                }
            }
        }
    }
}