package com.aueb.healthmonitor.ui.vitalsscreen.menu

import com.aueb.healthmonitor.enums.HealthRecordType

data class HealthRecordMenuItem(
    val id: Int = 0,
    val name: String = "",
    val type: HealthRecordType = HealthRecordType.None
)
