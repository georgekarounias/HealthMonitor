package com.aueb.healthmonitor

import android.app.Application
import com.aueb.healthmonitor.healthconnect.HealthConnectManager


class BaseApplication : Application() {
    val healthConnectManager by lazy {
        HealthConnectManager(this)
    }
}