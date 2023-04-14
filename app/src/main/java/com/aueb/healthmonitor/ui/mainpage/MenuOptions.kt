package com.aueb.healthmonitor.ui.mainpage

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aueb.healthmonitor.R
import com.aueb.healthmonitor.healthconnect.HealthConnectManager
import com.aueb.healthmonitor.patient.PatientManager
import com.aueb.healthmonitor.ui.AppScreens
import com.aueb.healthmonitor.ui.Screen
import com.aueb.healthmonitor.ui.drawer.DrawerBody
import com.aueb.healthmonitor.ui.drawer.DrawerHeader
import com.aueb.healthmonitor.ui.drawer.MenuItem
import com.aueb.healthmonitor.ui.theme.NavigationDrawerComposeTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MenuOptions(healthConnectManager: HealthConnectManager, patientManager: PatientManager) {
    NavigationDrawerComposeTheme {
        val scaffoldState = rememberScaffoldState()
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val availability by healthConnectManager.availability
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                com.aueb.healthmonitor.ui.navigationbar.AppBar(
                    onNavigationIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                DrawerHeader()
                DrawerBody(
                    items = listOf(
                        MenuItem(
                            id = "home",
                            title = stringResource(id = R.string.menu_item_home),
                            contentDescription = "Go to home screen",
                            screenInfo = Screen.HomeScreen,
                            icon = Icons.Default.Home
                        ),
                        MenuItem(
                            id = "patient",
                            title = stringResource(id = R.string.menu_item_patient),
                            contentDescription = "Go to patient screen",
                            screenInfo = Screen.PatientScreen,
                            icon = Icons.Default.SupervisedUserCircle
                        ),
                        MenuItem(
                            id = "vitals",
                            title = stringResource(id = R.string.menu_item_vitals),
                            contentDescription = "Opening vitals",
                            screenInfo = Screen.VitalsScreen,
                            icon = Icons.Default.MonitorHeart
                        ),
                        MenuItem(
                            id = "info",
                            title = stringResource(id = R.string.menu_item_info),
                            contentDescription = "Get help",
                            screenInfo = Screen.InfoScreen,
                            icon = Icons.Default.Info
                        ),
                        MenuItem(
                            id = "setting",
                            title = stringResource(id = R.string.menu_item_settings),
                            contentDescription = "Settings",
                            screenInfo = Screen.SettingScreen,
                            icon = Icons.Default.Settings
                        ),
                    ),
                    onItemClick = {
                        navController.navigate(it.screenInfo.route){
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                )
            }
        ) {
            AppScreens(navController, healthConnectManager, patientManager, scaffoldState)
        }
    }
}
