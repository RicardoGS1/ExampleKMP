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
import com.virtualworld.multiplatformiot.ui.core.component.DEFAULT_ARC_HEIGHT_ARDUINOS
import com.virtualworld.multiplatformiot.ui.core.component.DEFAULT_ARC_HEIGHT_DETAIL
import com.virtualworld.multiplatformiot.ui.core.component.DEFAULT_ARC_HEIGHT_MENU
import com.virtualworld.multiplatformiot.ui.core.component.DEFAULT_RECT_HEIGHT_ARDUINOS
import com.virtualworld.multiplatformiot.ui.core.component.DEFAULT_RECT_HEIGHT_DETAIL
import com.virtualworld.multiplatformiot.ui.core.component.DEFAULT_RECT_HEIGHT_MENU
import com.virtualworld.multiplatformiot.ui.core.component.TopBarCanva
import com.wirtualworld.multiplatformiot.feature.conectionLocal.navigations.ConectionLocalNavigation
import com.wirtualworld.multiplatformiot.feature.connectionBLE.navigations.ConnectionBLENavigation
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.navigations.ConnectionBluetoothNavigation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val paddingSinBarValues = WindowInsets.waterfall.asPaddingValues()

    var defaultArcSize by remember { mutableStateOf(DEFAULT_ARC_HEIGHT_MENU.dp) }
    var defaultRectSize by remember { mutableStateOf(DEFAULT_RECT_HEIGHT_MENU.dp) }

    var animateRect by remember { mutableStateOf(true) }

    val valueScroll = { valueSize: Dp ->
        defaultRectSize = valueSize
        if (valueSize != DEFAULT_RECT_HEIGHT_ARDUINOS.dp)
            animateRect = false
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route


    LaunchedEffect(currentRoute) {
        when (currentRoute) {

            MenuNavigation.Menu.route -> {
                animateRect = true
                defaultArcSize = DEFAULT_ARC_HEIGHT_MENU.dp
                defaultRectSize = DEFAULT_RECT_HEIGHT_MENU.dp
            }

            ConectionInternetNavigation.ConectionInternet.route -> {
                animateRect = true
                defaultArcSize = DEFAULT_ARC_HEIGHT_ARDUINOS.dp
            }

            ConnectionBluetoothNavigation.ConnectionBluetooth.route -> {
                animateRect = true
                defaultArcSize = DEFAULT_ARC_HEIGHT_ARDUINOS.dp
            }

            ConnectionBLENavigation.ConnectionBluetoothLE.route -> {
                animateRect = true
                defaultArcSize = DEFAULT_ARC_HEIGHT_ARDUINOS.dp
            }

            ConectionLocalNavigation.ConectionLocal.route -> {
                animateRect = true
                defaultArcSize = DEFAULT_ARC_HEIGHT_ARDUINOS.dp
            }

            ConectionInternetNavigation.DetailArduino("").route -> {
                animateRect = true
                defaultArcSize = DEFAULT_ARC_HEIGHT_DETAIL.dp
                defaultRectSize = DEFAULT_RECT_HEIGHT_DETAIL.dp
            }

            ConnectionBluetoothNavigation.DetailArduino("","").route -> {
                animateRect = true
                defaultArcSize = DEFAULT_ARC_HEIGHT_DETAIL.dp
                defaultRectSize = DEFAULT_RECT_HEIGHT_DETAIL.dp
            }

            ConnectionBLENavigation.DetailArduino("","").route -> {
                animateRect = true
                defaultArcSize = DEFAULT_ARC_HEIGHT_DETAIL.dp
                defaultRectSize = DEFAULT_RECT_HEIGHT_DETAIL.dp
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {

        TopBarCanva(defaultArcSize, defaultRectSize, animateRect)

        AppNavHost(navController, paddingSinBarValues, valueScroll)

    }
}