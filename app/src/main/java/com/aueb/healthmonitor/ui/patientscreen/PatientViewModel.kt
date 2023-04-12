package com.aueb.healthmonitor.ui.patientscreen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aueb.healthmonitor.ui.getGernderListFirstCode
import com.aueb.healthmonitor.utils.dateToIsoString
import com.aueb.healthmonitor.utils.toastMessage
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class PatientViewModel(private val context: Context): ViewModel(){

    var isFormValidated: Boolean by mutableStateOf(false)
    var readOnly: Boolean by mutableStateOf(false)
    var name: String by mutableStateOf("")
    var surname: String by  mutableStateOf("")
    var gender: String by  mutableStateOf(getGernderListFirstCode())
    var birthdate: Date by  mutableStateOf(Date())
    var birthdateStr: String by  mutableStateOf("")

    fun UpdateName(value: String){
        name = value.trim()
        isFormValidated = isFormValid()
    }
    fun UpdateSurname(value: String){
        surname = value.trim()
        isFormValidated = isFormValid()
    }
    fun UpdateGender(value: String){
        gender = value.trim()
        isFormValidated = isFormValid()
    }
    fun UpdateBirthDate(value: Date){
        birthdate = value
        birthdateStr = try{dateToIsoString(birthdate).trim()}catch (e: Exception){""}
        isFormValidated = isFormValid()
    }

    //TODO : Fix logic: make a fhir call to check if client exists. If exists show info else show form
    private fun doesPatientIdAlreadyExist(){
        readOnly = false
    }

    private fun isFormValid(): Boolean{
        if(name == "" ||
                surname == "" ||
                gender == "" ||
                birthdateStr == ""){
            return false
        }
        if(readOnly){
            return false
        }
        return true
    }
    //TODO: fix logic for save patient
    fun savePatient(){
        viewModelScope.launch {
            toastMessage(context, name+"/"+surname+"/"+gender+"/"+dateToIsoString(birthdate))
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