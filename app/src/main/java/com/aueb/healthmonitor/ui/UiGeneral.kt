package com.aueb.healthmonitor.ui

import com.aueb.healthmonitor.R

enum class Screen(val route: String, val titleId: Int, val hasMenuItem: Boolean = true) {
    HomeScreen("home_screen", R.string.home_screen),
    PatientScreen("patient_screen", R.string.patient_screen),
    VitalsScreen("vitals_screen", R.string.vitals_screen),
    InfoScreen("info_screen", R.string.info_screen)
}