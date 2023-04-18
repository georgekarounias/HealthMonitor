package com.aueb.healthmonitor.ui.vitalsscreen

import android.content.Context
import android.os.RemoteException
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.records.BloodPressureRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.OxygenSaturationRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aueb.healthmonitor.healthconnect.HealthConnectManager
import com.aueb.healthmonitor.healthconnect.HealthData
import com.aueb.healthmonitor.patient.PatientManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset
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

    init {
        checkPermissions()
    }

    fun checkPermissions(){
        viewModelScope.launch(Dispatchers.Main) {
            tryWithPermissionsCheck {

            }
        }
    }

    fun initialize(){
        val startDate = LocalDate.of(2023, Month.APRIL, 9)
        val start = startDate.atStartOfDay(ZoneOffset.UTC).toInstant()
        val endDate = LocalDate.of(2023, Month.APRIL, 11)
        val end = endDate.atStartOfDay(ZoneOffset.UTC).toInstant()
        viewModelScope.launch(Dispatchers.IO) {
            tryWithPermissionsCheck {
                val data = healthConnectManager.readHealthData(start, end)
                withContext(Dispatchers.Main){
                    healthData.value = data
                }
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