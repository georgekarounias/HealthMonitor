package com.aueb.healthmonitor.staticVariables

class StaticVariables {
    companion object{
        const val BASE_URL = "http://192.168.2.3:8080/fhir/"
        const val AppSharedPreferences = "health_monitor_preferences"
        const val ASP_PatientId = "patientUuid"
        const val ASP_PatientName = "patientName"
        const val ASP_PatientSurname = "patientSurname"
        const val ASP_PatientNameHash = "patientNameHash"
        const val ASP_PatientSurnameHash = "patientSurnameHash"
    }
}