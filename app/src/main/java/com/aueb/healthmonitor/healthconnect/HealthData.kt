package com.aueb.healthmonitor.healthconnect

import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.records.BloodPressureRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.OxygenSaturationRecord

data class HealthData (
    val heartRateMeasurements: List<HeartRateRecord> = listOf(),
    val bloodSugarMeasurements: List<BloodGlucoseRecord> = listOf(),
    val bloodPressureMeasurements: List<BloodPressureRecord> = listOf(),
    val spo2Measurements: List<OxygenSaturationRecord> = listOf()
)