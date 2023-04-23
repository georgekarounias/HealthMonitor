package com.aueb.healthmonitor.ui.patientscreen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.aueb.healthmonitor.R
import com.aueb.healthmonitor.fhirclient.FhirServices
import com.aueb.healthmonitor.patient.PatientManager
import com.aueb.healthmonitor.ui.Screen
import com.aueb.healthmonitor.ui.getGenderByCode
import com.aueb.healthmonitor.ui.getGernderListFirstCode
import com.aueb.healthmonitor.utils.dateToIsoString
import com.aueb.healthmonitor.utils.toastMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.hl7.fhir.r4.model.Patient
import java.util.Date
import kotlin.coroutines.suspendCoroutine


class PatientViewModel(private val context: Context, private val patientManager: PatientManager, private val navController: NavController): ViewModel(){

    var isFormValidated: Boolean by mutableStateOf(false)
    var isLoading: Boolean by mutableStateOf(false)
    var loadingTitle: String by mutableStateOf("")
    var loadingText: String by mutableStateOf("")
    var readOnly: Boolean by mutableStateOf(false)
    var name: String by mutableStateOf("")
    var surname: String by  mutableStateOf("")
    var gender: String by  mutableStateOf(getGernderListFirstCode())
    var birthdate: Date by  mutableStateOf(Date())
    var birthdateStr: String by  mutableStateOf("")
    var smartwachmodel: String by  mutableStateOf("")
    var smartwachManufacturer: String by  mutableStateOf("")
    var isFhirRequestPatientCreated: Boolean by mutableStateOf(false)

    init{
        initPatient()
    }

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

    fun UpdateSmartwatchModel(value: String){
        smartwachmodel = value.trim()
        isFormValidated = isFormValid()
    }

    fun UpdateSmartwatchManufacturer(value: String){
        smartwachManufacturer = value.trim()
        isFormValidated = isFormValid()
    }

    private fun setLoaderInfo(_show: Boolean, _title: String, _text: String){
        isLoading = _show
        loadingText = _text
        loadingTitle = _title
    }
    private fun initPatient(){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                setLoaderInfo(true, context.resources.getString(R.string.loader_resource_title), context.resources.getString(R.string.loader_resource_text))
            }
            val patientId = patientManager.GetId()
            if(patientId!=null) {
                val patient = FhirServices.getPatientByIdentifier(patientId, context)
                val device = FhirServices.getConnectedDevices(patientId, context)
                withContext(Dispatchers.Main) {
                    if (patient != null) {
                        readOnly = patientManager.checkIfPatientHasId(patient as Patient, patientId)
                        if (readOnly) {
                            name = patientManager.GetName() ?: ""
                            surname = patientManager.GetSurname() ?: ""
                            gender = patient.gender?.toCode() ?: ""
                            birthdate = patient.birthDate ?: Date()
                            birthdateStr = dateToIsoString(patient.birthDate)
                            smartwachmodel = device.firstOrNull()?.modelNumber ?: ""
                            smartwachManufacturer = device.firstOrNull()?.manufacturer ?: ""
                        }
                    }
                }
            }
            withContext(Dispatchers.Main){
                setLoaderInfo(false, "", "")
            }
        }
    }

    private fun isFormValid(): Boolean{
        if(name.isNullOrEmpty() ||
                surname.isNullOrEmpty()||
                gender.isNullOrEmpty()||
                birthdateStr.isNullOrEmpty() ||
                smartwachmodel.isNullOrEmpty() ||
                smartwachManufacturer.isNullOrEmpty()){
            return false
        }
        if(readOnly){
            return false
        }
        return true
    }

    fun savePatient(){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                setLoaderInfo(true, context.resources.getString(R.string.loader_resource_title), context.resources.getString(R.string.loader_resource_text))
            }
            val fhirGender = getGenderByCode(gender)

            isFhirRequestPatientCreated = FhirServices.createPatient(patientManager.GetId() ?: "",
                name,
                surname,
                fhirGender,
                birthdate,
                context,
                patientManager,
                smartwachManufacturer,
                smartwachmodel
            )
            withContext(Dispatchers.Main){
                setLoaderInfo(false, "", "")
                if(isFhirRequestPatientCreated){
                    //TODO: needs some fixing here (not a priority for now)
//                    navController.navigate(Screen.HomeScreen.route){
//                        navController.graph.startDestinationRoute?.let { route ->
//                            popUpTo(route) {
//                                saveState = true
//                            }
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
                    readOnly = true
                }
            }
        }

    }

}

class PatientViewModelFactory(private val context: Context, private val patientManager: PatientManager, private val navController: NavController) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientViewModel(context, patientManager, navController) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}