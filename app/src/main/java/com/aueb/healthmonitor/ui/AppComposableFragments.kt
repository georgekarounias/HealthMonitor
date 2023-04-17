package com.aueb.healthmonitor.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.health.connect.client.HealthConnectClient
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aueb.healthmonitor.healthconnect.HealthConnectManager
import com.aueb.healthmonitor.patient.PatientManager
import com.aueb.healthmonitor.ui.homescreen.HomeScreen
import com.aueb.healthmonitor.ui.infoscreen.InfoScreen
import com.aueb.healthmonitor.ui.patientscreen.PatienScreen
import com.aueb.healthmonitor.ui.patientscreen.PatientViewModel
import com.aueb.healthmonitor.ui.patientscreen.PatientViewModelFactory
import com.aueb.healthmonitor.ui.settingsscreen.SettingsScreen
import com.aueb.healthmonitor.ui.vitalsscreen.VitalsScreen

@Composable
fun AppScreens(
    navController: NavHostController,
    healthConnectManager: HealthConnectManager,
    patientManager: PatientManager,
    scaffoldState: ScaffoldState
){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        val availability by healthConnectManager.availability
        composable(Screen.HomeScreen.route){
            HomeScreen(
                healthConnectAvailability = availability,
                onResumeAvailabilityCheck = {
                    healthConnectManager.checkAvailability()
                }
            )
        }
        composable(Screen.PatientScreen.route){
            PatienScreen(navController, context, patientManager)
        }
        composable(Screen.VitalsScreen.route){
            VitalsScreen()
        }
        composable(Screen.InfoScreen.route){
            InfoScreen()
        }
        composable(Screen.SettingScreen.route){
            SettingsScreen()
        }
    }
}