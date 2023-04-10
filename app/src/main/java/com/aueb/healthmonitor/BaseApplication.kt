package com.aueb.healthmonitor

import android.app.Application
import com.aueb.healthmonitor.healthconnect.HealthConnectManager
import com.aueb.healthmonitor.patient.PatientManager


class BaseApplication : Application() {
    val healthConnectManager by lazy {
        HealthConnectManager(this)
    }
    val patientManager by lazy {
        PatientManager(this)
    }
}