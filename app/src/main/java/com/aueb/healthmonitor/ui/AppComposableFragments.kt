package com.aueb.healthmonitor.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aueb.healthmonitor.healthconnect.HealthConnectManager
import com.aueb.healthmonitor.ui.homescreen.HomeScreen
import com.aueb.healthmonitor.ui.infoscreen.InfoScreen
import com.aueb.healthmonitor.ui.patientscreen.PatienScreen
import com.aueb.healthmonitor.ui.vitalsscreen.VitalsScreen

@Composable
fun AppScreens(
    navController: NavHostController,
    healthConnectManager: HealthConnectManager,
    scaffoldState: ScaffoldState
){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        val availability by healthConnectManager.availability
        composable(Screen.HomeScreen.route){
            HomeScreen()
        }
        composable(Screen.PatientScreen.route){
            PatienScreen()
        }
        composable(Screen.VitalsScreen.route){
            VitalsScreen()
        }
        composable(Screen.InfoScreen.route){
            InfoScreen()
        }
    }
}