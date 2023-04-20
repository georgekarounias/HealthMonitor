package com.aueb.healthmonitor.ui.vitalsscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aueb.healthmonitor.R
import com.aueb.healthmonitor.recordConverters.SetDataTableOptions
import com.aueb.healthmonitor.ui.components.CustomButton
import com.aueb.healthmonitor.ui.components.datatable.DataTable

@Composable
fun<T> VitalsFragment(data: List<T>, onSave: (data: List<T>)->Unit){
    val options = SetDataTableOptions(data)
    LazyColumn(
        modifier = Modifier.height(500.dp))
    {
        item {
            DataTable(options)
            if(data.isNullOrEmpty()){
                return@item
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButton(text = stringResource(R.string.vitals_screen_save_records), onClick = { onSave(data) })
            }
        }
    }
}