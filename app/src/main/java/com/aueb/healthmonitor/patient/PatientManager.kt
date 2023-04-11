package com.aueb.healthmonitor.patient

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import com.aueb.healthmonitor.staticVariables.StaticVariables
import com.aueb.healthmonitor.utils.hashString
import java.util.*

class PatientManager(private val context: Context){
    var patientUUID: String? = null

    var shouldCreatePatient = mutableStateOf(false)
        private set

    init {
        patientUUID = getOrCreateUUID().toString()
        checkAvailability()
    }

    private fun checkAvailability() {
        shouldCreatePatient.value = !isPatientSet()
    }

    fun SavePatientInfo(name: String, surname:String){
        setValueToSharedPreferences(StaticVariables.ASP_PatientName, name)
        setValueToSharedPreferences(StaticVariables.ASP_PatientSurname, surname)
        setValueToSharedPreferences(StaticVariables.ASP_PatientNameHash, hashString(name))
        setValueToSharedPreferences(StaticVariables.ASP_PatientSurname, hashString(surname))
    }

    fun GetName(): String?{
        return getValueFromSharedPreferences(StaticVariables.ASP_PatientName)
    }

    fun GetSurname(): String?{
        return getValueFromSharedPreferences(StaticVariables.ASP_PatientSurname)
    }

    fun GetId(): String?{
        return getValueFromSharedPreferences(StaticVariables.ASP_PatientId)
    }

    fun GetHashedName(): String?{
        return getValueFromSharedPreferences(StaticVariables.ASP_PatientNameHash)
    }

    fun GetHashedSurname(): String?{
        return getValueFromSharedPreferences(StaticVariables.ASP_PatientSurnameHash)
    }

    fun isPatientSet(): Boolean{
        if(GetName() == null || GetSurname() == null || GetHashedName() == null || GetHashedSurname() == null){
            return false
        }
        return true
    }

    fun VerifyPatientInfo(name:String, surname: String): Boolean{
        val hashedName = GetHashedName()
        val hashedSurname = GetHashedSurname()
        return (hashString(name) == hashedName && hashString(surname) == hashedSurname)
    }

    //TODO: function for changing profile
//    fun changePatientProfile(patientId: String, name: String, surname: String):Boolean{
//        //GetPatient from db by id
//        //chek hash(name) == db patient name
//        //same for surname
//        //if all true
//        //setValueToSharedPreferences(StaticVariables.ASP_PatientId, db.patienid)
//        //setValueToSharedPreferences(StaticVariables.ASP_PatientName, name)
//        //setValueToSharedPreferences(StaticVariables.ASP_PatientSurname, surname)
//        //setValueToSharedPreferences(StaticVariables.ASP_PatientNameHash, hashString(name))
//        //setValueToSharedPreferences(StaticVariables.ASP_PatientSurname, hashString(surname))
//    }

    fun GetStorePatientInfo(): AppPatientInfo{
        return AppPatientInfo(
            id = GetId(),
            name = GetName(),
            surname = GetSurname(),
            nameHash = GetHashedName(),
            surnameHash = GetHashedSurname()
        )
    }

    private fun getOrCreateUUID(): UUID {
        val sharedPreferences = context.getSharedPreferences(StaticVariables.AppSharedPreferences, Context.MODE_PRIVATE)
        val uuidString = sharedPreferences.getString(StaticVariables.ASP_PatientId, null)
        return if (uuidString != null) UUID.fromString(uuidString) else generateAndSaveUUID(sharedPreferences)
    }

    private fun generateAndSaveUUID(sharedPreferences: SharedPreferences): UUID {
        val uuid = UUID.randomUUID()
        sharedPreferences.edit().putString(StaticVariables.ASP_PatientId, uuid.toString()).apply()
        return uuid
    }

    private fun setValueToSharedPreferences(key: String, value: String){
        val sharedPreferences = context.getSharedPreferences(StaticVariables.AppSharedPreferences, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getValueFromSharedPreferences(key: String): String? {
        val sharedPreferences = context.getSharedPreferences(StaticVariables.AppSharedPreferences, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }
}