package com.aueb.healthmonitor.ui.patientscreen

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aueb.healthmonitor.utils.toastMessage
import kotlinx.coroutines.launch

class PatientViewModel(private val context: Context): ViewModel(){

    var name = mutableStateOf("")
    var surname = mutableStateOf("")
    var gender = mutableStateOf("")
    var birthdate = mutableStateOf("")

    //TODO: fix logic for save patient
    fun savePatient(){
        viewModelScope.launch {
            toastMessage(context, name.value+"/"+surname.value+"/"+gender.value)
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