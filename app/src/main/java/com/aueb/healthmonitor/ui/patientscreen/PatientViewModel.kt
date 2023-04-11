package com.aueb.healthmonitor.ui.patientscreen

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aueb.healthmonitor.patient.AppPatientInfo
import com.aueb.healthmonitor.utils.toastMessage
import kotlinx.coroutines.launch

class PatientViewModel(private val context: Context): ViewModel(){

    var appPatientInfo: MutableState<AppPatientInfo> = mutableStateOf(AppPatientInfo())

    //TODO: fix logic for save patient
    fun savePatient(){
        viewModelScope.launch {
            toastMessage(context, appPatientInfo.value.name +" "+ appPatientInfo.value.surname)
        }
    }

}

class PatientViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}