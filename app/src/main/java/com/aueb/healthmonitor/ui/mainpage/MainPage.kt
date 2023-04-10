package com.aueb.healthmonitor.ui.mainpage

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aueb.healthmonitor.R
import com.aueb.healthmonitor.ui.drawer.DrawerBody
import com.aueb.healthmonitor.ui.drawer.DrawerHeader
import com.aueb.healthmonitor.ui.drawer.MenuItem
import com.aueb.healthmonitor.ui.theme.NavigationDrawerComposeTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainPageApp() {
    NavigationDrawerComposeTheme {
        val scaffoldState = rememberScaffoldState()
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        //val availability by healthConnectManager.availability
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
                            id = "patient",
                            title = stringResource(id = R.string.menu_item_patient),
                            contentDescription = "Go to home screen",
                            icon = Icons.Default.SupervisedUserCircle
                        ),
                        MenuItem(
                            id = "vitals",
                            title = stringResource(id = R.string.menu_item_vitals),
                            contentDescription = "Opening vitals",
                            icon = Icons.Default.MonitorHeart
                        ),
                        MenuItem(
                            id = "info",
                            title = stringResource(id = R.string.menu_item_info),
                            contentDescription = "Get help",
                            icon = Icons.Default.Info
                        ),
                    ),
                    onItemClick = {
//                        when(it.id){
//                            "patient" -> navController.navigate(item.route)
//                        }
                        println("Clicked on ${it.title}")
                    }
                )
            }
        ) {

        }
    }
}
