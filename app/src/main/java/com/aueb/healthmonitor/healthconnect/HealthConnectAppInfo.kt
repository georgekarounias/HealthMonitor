package com.aueb.healthmonitor.healthconnect

import android.graphics.drawable.Drawable

data class HealthConnectAppInfo (
    val packageName: String,
    val appLabel: String,
    val icon: Drawable?
)