package com.aueb.healthmonitor.ui.vitalsscreen

import android.content.Context
import android.os.RemoteException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.records.BloodPressureRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.OxygenSaturationRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aueb.healthmonitor.R
import com.aueb.healthmonitor.enums.HealthRecordType
import com.aueb.healthmonitor.fhirclient.FhirServices
import com.aueb.healthmonitor.healthconnect.HealthConnectManager
import com.aueb.healthmonitor.healthconnect.HealthData
import com.aueb.healthmonitor.patient.PatientManager
import com.aueb.healthmonitor.ui.getGenderByCode
import com.aueb.healthmonitor.ui.vitalsscreen.menu.HealthRecordMenuItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date
import java.util.UUID

class VitalsViewModel(private val context: Context, private val patientManager: PatientManager, private val healthConnectManager: HealthConnectManager): ViewModel(){

    val permissions = setOf(
        HealthPermission.getReadPermission(HeartRateRecord::class),
        HealthPermission.getReadPermission(BloodGlucoseRecord::class),
        HealthPermission.getReadPermission(BloodPressureRecord::class),
        HealthPermission.getReadPermission(OxygenSaturationRecord::class),
    )
    val permissionsGranted = mutableStateOf(false)

    val permissionsLauncher = healthConnectManager.requestPermissionsActivityContract()

    val healthData = mutableStateOf(HealthData())

    val selectedDay = mutableStateOf(Date())
    var isLoading: Boolean by mutableStateOf(false)

    var selectedMenuItem = mutableStateOf(HealthRecordMenuItem())

    init {
        checkPermissions()
        loadHealthData()
    }

    fun checkPermissions(){
        viewModelScope.launch(Dispatchers.Main) {
            tryWithPermissionsCheck {

            }
        }
    }

    fun loadHealthData(){
        if(selectedDay.value == null){
            return
        }
        val calendar = Calendar.getInstance()
        calendar.time = selectedDay.value
        val startDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))
        val start = startDate.atStartOfDay(ZoneOffset.UTC).toInstant()
        val endDate = startDate.plusDays(1)
        val end = endDate.atStartOfDay(ZoneOffset.UTC).toInstant()

        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            tryWithPermissionsCheck {
                val data = healthConnectManager.readHealthData(start, end)
                withContext(Dispatchers.Main){
                    healthData.value = data
                    isLoading = false
                }
            }
        }
    }

    fun updateSelectDay(selectedDate: Date){
        selectedDay.value = selectedDate
        loadHealthData()
    }

    fun updateHealthRecordType(item: HealthRecordMenuItem){
        selectedMenuItem.value = item
    }

    fun onSaveHR(data: List<HeartRateRecord>){
        //TODO: maybe create a hash for the (day + type + patient + records) and create an id
        //TODO: check if id already exists. if true we know that this patient for this day has already upload the type and the exact records (so workflow ends).
        //TODO: Else -> Convert [HRConverted is ready and untested] and submit records to hapi fhir
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                isLoading = true
            }
            async {
                FhirServices.createHeartRateObservations(healthData.value.heartRateMeasurements,patientManager.GetId() ?: "", context)}.await()
            withContext(Dispatchers.Main){
                isLoading = false
            }
        }
    }
    fun onSaveO2sp(data: List<OxygenSaturationRecord>){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                isLoading = true
            }
            async {
                FhirServices.createO2spObservations(healthData.value.spo2Measurements,patientManager.GetId() ?: "", context)}.await()
            withContext(Dispatchers.Main){
                isLoading = false
            }
        }
    }
    fun onSaveBP(data: List<BloodPressureRecord>){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                isLoading = true
            }
            async {
                FhirServices.createBPObservations(healthData.value.bloodPressureMeasurements,patientManager.GetId() ?: "", context)}.await()
            withContext(Dispatchers.Main){
                isLoading = false
            }
        }
    }
    fun onSaveBG(data: List<BloodGlucoseRecord>){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                isLoading = true
            }
            async {
                FhirServices.createBGObservations(healthData.value.bloodSugarMeasurements,patientManager.GetId() ?: "", context)}.await()
            withContext(Dispatchers.Main){
                isLoading = false
            }
        }
    }
    private suspend fun tryWithPermissionsCheck(block: suspend () -> Unit) {
        permissionsGranted.value = healthConnectManager.hasAllPermissions(permissions)
        if (permissionsGranted.value) {
            block()
        }
    }

    sealed class UiState {
        object Uninitialized : UiState()
        object Done : UiState()

        // A random UUID is used in each Error object to allow errors to be uniquely identified,
        // and recomposition won't result in multiple snackbars.
        data class Error(val exception: Throwable, val uuid: UUID = UUID.randomUUID()) : UiState()
    }

}


class VitalsViewModelFactory(private val context: Context, private val patientManager: PatientManager, private val healthConnectManager: HealthConnectManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VitalsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VitalsViewModel(context, patientManager, healthConnectManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}