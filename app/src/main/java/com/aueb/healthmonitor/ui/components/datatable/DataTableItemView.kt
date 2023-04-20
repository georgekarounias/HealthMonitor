package com.aueb.healthmonitor.ui.components.datatable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.PunchClock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aueb.healthmonitor.ui.components.customdivider.CustomDivider

@Composable
fun DataTableItemView(icon: ImageVector, value: String, date: String) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )
        }
        Row{
            Icon(
                imageVector = Icons.Default.PunchClock,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = date,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
        CustomDivider()
    }
}