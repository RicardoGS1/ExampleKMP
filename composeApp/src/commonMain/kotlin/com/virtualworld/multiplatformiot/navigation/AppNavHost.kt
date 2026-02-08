package com.virtualworld.multiplatformiot.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.virtualworld.multiplatformiot.feature.conectionInternet.navigations.ConectionInternetNavigation
import com.virtualworld.multiplatformiot.feature.conectionInternet.navigations.conectionInternetGraph
import com.virtualworld.multiplatformiot.feature.login.navigations.LoginNavigation
import com.virtualworld.multiplatformiot.feature.login.navigations.loginRoutesGraph
import com.virtualworld.multiplatformiot.feature.menu.navigations.MenuNavigation
import com.virtualworld.multiplatformiot.feature.menu.navigations.menuRoutesGraph
import com.wirtualworld.multiplatformiot.feature.conectionLocal.navigations.ConectionLocalNavigation
import com.wirtualworld.multiplatformiot.feature.conectionLocal.navigations.conectionLocalRoute
import com.wirtualworld.multiplatformiot.feature.connectionBLE.navigations.ConnectionBLENavigation
import com.wirtualworld.multiplatformiot.feature.connectionBLE.navigations.connectionBLEGraph
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.navigations.ConnectionBluetoothNavigation
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.navigations.connectionBluetoothGraph


@Composable
fun AppNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    valueScroll: (Dp) -> Unit,
    showGoogleSignIn: Boolean = true,
    onGoogleSignInRequest: ((String) -> Unit) -> Unit = {},
    startDestination: String,
) {

    NavHost(
        navController,
        startDestination = startDestination,
        modifier = Modifier.padding(paddingValues)
    ) {

        loginRoutesGraph(
            onNavigateToRegister = { navController.navigate(LoginNavigation.Register.route) },
            onNavigateToMenu = {
                navController.navigate(MenuNavigation.Menu.route) {
                    popUpTo(LoginNavigation.Login.route) { inclusive = true }
                }
            },
            onNavigateBack = { navController.popBackStack() },
            showGoogleSignIn = showGoogleSignIn,
            onGoogleSignInRequest = onGoogleSignInRequest,
        )

        menuRoutesGraph(
            goToLocalConection = { navController.navigate(ConectionLocalNavigation.ConectionLocal.route) },
            goToInternetConection = { navController.navigate(ConectionInternetNavigation.ConectionInternet.route) },
            goToBluetoothConection = {navController.navigate(ConnectionBluetoothNavigation.ConnectionBluetooth.route)},
            goToBluetoothLEConection = {navController.navigate(ConnectionBLENavigation.ConnectionBluetoothLE.route)}
        )

        conectionLocalRoute(
            popBackStack = { navController.popBackStack() },
            valueScroll = valueScroll
        )

        conectionInternetGraph(
            navController = navController,
            valueScroll = valueScroll
        )

        connectionBluetoothGraph(
            navController = navController,
            valueScroll = valueScroll
        )

        connectionBLEGraph(
            navController = navController,
            valueScroll = valueScroll
        )

    }


}
