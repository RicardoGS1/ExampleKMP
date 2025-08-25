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
import com.virtualworld.multiplatformiot.feature.menu.navigations.MenuNavigation
import com.virtualworld.multiplatformiot.feature.menu.navigations.menuRoutes
import com.wirtualworld.multiplatformiot.feature.conectionLocal.navigations.conectionLocalRoute
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.navigations.ConnectionBluetoothNavigation
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.navigations.connectionBluetoothGraph


@Composable
fun AppNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    valueScroll: (Dp) -> Unit,
) {

    NavHost(
        navController,
        startDestination = MenuNavigation.Menu.route,
        modifier = Modifier.padding(paddingValues)
    ) {


        menuRoutes(
            goToLocalConection = { navController.navigate("conectionLocal") },
            goToInternetConection = { navController.navigate(ConectionInternetNavigation.ConectionInternet.route) },
            goToBluetoothConection = {navController.navigate(ConnectionBluetoothNavigation.ConnectionBluetooth.route)}
        )

        conectionLocalRoute(
            popBackStack = { navController.popBackStack() }
        )

        conectionInternetGraph(
            navController = navController,valueScroll
        )

        connectionBluetoothGraph(
            navController = navController
        )

    }


}
