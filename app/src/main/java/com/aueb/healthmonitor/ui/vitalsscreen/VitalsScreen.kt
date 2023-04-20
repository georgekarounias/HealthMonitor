package com.aueb.healthmonitor.ui.vitalsscreen

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aueb.healthmonitor.healthconnect.HealthConnectManager
import com.aueb.healthmonitor.patient.PatientManager
import com.aueb.healthmonitor.recordConverters.SetDataTableOptions
import com.aueb.healthmonitor.ui.components.datatable.DataTable

@Composable
fun VitalsScreen(navController: NavController, context: Context, patientManager: PatientManager, healthConnectManager: HealthConnectManager){
    val viewModel: VitalsViewModel = viewModel(
        factory = VitalsViewModelFactory(context, patientManager, healthConnectManager)
    )
    val permissionsGranted by viewModel.permissionsGranted
    val permissions = viewModel.permissions
    val healthData = viewModel.healthData
    val onPermissionsResult = {viewModel.initialize()}
    val permissionsLauncher =
        rememberLauncherForActivityResult(viewModel.permissionsLauncher) {
            onPermissionsResult()}
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!permissionsGranted) {
            item {
                Button(
                    onClick = { permissionsLauncher.launch(permissions) }
                ) {
                    Text(text = "Get Perms")
                }
            }
        }else{
            item{
                val options = SetDataTableOptions(healthData.value.spo2Measurements)
                DataTable(options)
            }
        }

    }
}