package com.virtualworld.multiplatformiot.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.waterfall
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.virtualworld.multiplatformiot.feature.conectionInternet.navigations.ConectionInternetNavigation
import com.virtualworld.multiplatformiot.feature.menu.navigations.MenuNavigation
import com.virtualworld.multiplatformiot.navigation.AppNavHost
import com.virtualworld.multiplatformiot.ui.core.component.TopBarCanva
import com.wirtualworld.multiplatformiot.feature.conectionLocal.navigations.ConectionLocal
import com.wirtualworld.multiplatformiot.feature.conectionLocal.navigations.ConectionLocalNavigation
import com.wirtualworld.multiplatformiot.feature.connectionBLE.navigations.ConnectionBLENavigation
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.navigations.ConnectionBluetoothNavigation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val paddingSinBarValues = WindowInsets.waterfall.asPaddingValues()
    val paddingWhitBarValues = WindowInsets.safeDrawing.asPaddingValues()

    var endArcAnimated by remember { mutableStateOf(0f) }
    var endSizeRect by remember { mutableStateOf(200.dp) }
    var sizeArc by remember { mutableStateOf(100.dp) }
    var animateRect by remember { mutableStateOf(true) }

    val valueScroll = { valueSize: Dp ->
        endSizeRect = valueSize
        if (valueSize != 200.dp)
            animateRect = false
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route


    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            MenuNavigation.Menu.route -> {
                animateRect = true
                endArcAnimated = 1f
                endSizeRect = 200.dp
            }

            ConectionInternetNavigation.ConectionInternet.route -> {
                animateRect = true
                endArcAnimated = 0f
            }

            ConnectionBluetoothNavigation.ConnectionBluetooth.route -> {
                animateRect = true
                endArcAnimated = 0f
            }

            ConnectionBLENavigation.ConnectionBluetoothLE.route -> {
                animateRect = true
                endArcAnimated = 0f
            }

            ConectionLocalNavigation.ConectionLocal.route -> {
                animateRect = true
                endArcAnimated = 0f
            }

            ConectionInternetNavigation.DetailArduino("").route -> {
                animateRect = true
                endArcAnimated = 0f
                endSizeRect = 120.dp
            }

            ConnectionBluetoothNavigation.DetailArduino("","").route -> {
                animateRect = true
                endArcAnimated = 0f
                endSizeRect = 120.dp
            }

            ConnectionBLENavigation.DetailArduino("","").route -> {
                animateRect = true
                endArcAnimated = 0f
                endSizeRect = 120.dp
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {

        TopBarCanva(endArcAnimated, endSizeRect, sizeArc, animateRect)

        AppNavHost(navController, paddingSinBarValues, valueScroll)

    }
}