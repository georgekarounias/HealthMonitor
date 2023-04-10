package com.aueb.healthmonitor.healthconnect

import android.app.Application

class BaseHealthConnectApp : Application() {
    val healthConnectManager by lazy {
        HealthConnectManager(this)
    }
}