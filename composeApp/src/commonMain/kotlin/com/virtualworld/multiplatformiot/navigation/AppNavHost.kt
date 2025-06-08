package com.virtualworld.multiplatformiot.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.virtualworld.multiplatformiot.feature.conectionInternet.navigations.ConectionInternetNavigation
import com.virtualworld.multiplatformiot.feature.conectionInternet.navigations.conectionInternetGraph
import com.virtualworld.multiplatformiot.feature.menu.navigations.Menu
import com.virtualworld.multiplatformiot.feature.menu.navigations.menuRoutes
import com.wirtualworld.multiplatformiot.feature.conectionLocal.navigations.ConectionLocal
import com.wirtualworld.multiplatformiot.feature.conectionLocal.navigations.conectionLocalRoute


@Composable
fun AppNavHost(navController: NavHostController, paddingValues: PaddingValues) {

    NavHost(
        navController,
        startDestination = Menu,
        modifier = Modifier.padding(paddingValues)
    ) {


        menuRoutes(
            goToLocalConection = { navController.navigate(ConectionLocal) },
            goToInternetConection = { navController.navigate(ConectionInternetNavigation.ConectionInternet) },
            goToBluetoothConection = {}
        )

        conectionLocalRoute(
            popBackStack = { navController.popBackStack() }
        )

        conectionInternetGraph(
            navController = navController
        )

    }


}
