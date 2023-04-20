package com.aueb.healthmonitor.ui.vitalsscreen

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.aueb.healthmonitor.enums.HealthRecordType
import com.aueb.healthmonitor.healthconnect.HealthConnectManager
import com.aueb.healthmonitor.patient.PatientManager
import com.aueb.healthmonitor.recordConverters.SetDataTableOptions
import com.aueb.healthmonitor.ui.components.datatable.DataTable
import com.aueb.healthmonitor.ui.components.datepicker.DatePicker
import com.aueb.healthmonitor.ui.components.loader.LoadingDialog
import com.aueb.healthmonitor.ui.vitalsscreen.menu.HealthRecordMenu
import com.aueb.healthmonitor.ui.vitalsscreen.menu.HealthRecordMenuItem

@Composable
fun VitalsScreen(navController: NavController, context: Context, patientManager: PatientManager, healthConnectManager: HealthConnectManager){
    val viewModel: VitalsViewModel = viewModel(
        factory = VitalsViewModelFactory(context, patientManager, healthConnectManager)
    )
    val permissionsGranted by viewModel.permissionsGranted
    val permissions = viewModel.permissions
    val healthData = viewModel.healthData
    val onPermissionsResult = {viewModel.loadHealthData()}
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
                    Text(text = "Get Permissions")
                }
            }
        }else{
            item{
                HealthRecordMenu(
                    items = listOf(
                        HealthRecordMenuItem(
                            id = 1,
                            type = HealthRecordType.HeartRate,
                            name = "Heart Rate"
                        ),
                        HealthRecordMenuItem(
                            id = 2,
                            type = HealthRecordType.OxygenSaturation,
                            name = "Oxygen Saturation"
                        ),
                        HealthRecordMenuItem(
                            id = 3,
                            type = HealthRecordType.BloodGlucose,
                            name = "Blood Glucose"
                        ),
                        HealthRecordMenuItem(
                            id = 4,
                            type = HealthRecordType.BloodPressure,
                            name = "Blood Pressure"
                        )
                    ),
                    onSelected = {
                        viewModel.updateHealthRecordType(it)
                    },
                    selected = viewModel.selectedMenuItem.value
                )
                Spacer(modifier = Modifier.height(10.dp))
                DatePicker(
                    onDateSelected = {
                    viewModel.updateSelectDay(it)
                })
                LoadingDialog(viewModel.isLoading, "Loading Data", "Fetching Data from Health Connect")
                if(viewModel.isLoading){
                   return@item
                }
                Spacer(modifier = Modifier.height(10.dp))
                when(viewModel.selectedMenuItem.value.type){
                    HealthRecordType.HeartRate ->{
                        VitalsFragment(data = healthData.value.heartRateMeasurements, onSave = {viewModel.onSaveHR(healthData.value.heartRateMeasurements)})
                    }
                    HealthRecordType.OxygenSaturation ->{
                        VitalsFragment(data = healthData.value.spo2Measurements, onSave = {viewModel.onSaveO2sp(healthData.value.spo2Measurements)})
                    }
                    HealthRecordType.BloodPressure ->{
                        VitalsFragment(data = healthData.value.bloodPressureMeasurements, onSave = {viewModel.onSaveBP(healthData.value.bloodPressureMeasurements)})
                    }
                    HealthRecordType.BloodGlucose ->{
                        VitalsFragment(data = healthData.value.bloodSugarMeasurements, onSave = {viewModel.onSaveBG(healthData.value.bloodSugarMeasurements)})
                    }
                    else -> {
                        return@item
                    }
                }
            }
        }

    }
}