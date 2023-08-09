package com.aueb.healthmonitor.staticVariables

import android.provider.ContactsContract.CommonDataKinds.Email

class StaticVariables {
    companion object{
        const val BASE_URL = "http://hapi.fhir.org/baseR4/" //"http://192.168.2.3:8080/fhir/"
        const val AppSharedPreferences = "health_monitor_preferences"
        const val ASP_PatientId = "patientUuid"
        const val ASP_PatientName = "patientName"
        const val ASP_PatientSurname = "patientSurname"
        const val ASP_PatientNameHash = "patientNameHash"
        const val ASP_PatientSurnameHash = "patientSurnameHash"
        const val PatientIdSystemCode = "http://example.com/patient-identifier"
        const val TagSystem = "aueb-tag-system"
        const val TagName = "aueb-vitals-signs-survey"
        const val TagValueDisplay = "aueb-vitals-signs-survey-alpha"
    }
}