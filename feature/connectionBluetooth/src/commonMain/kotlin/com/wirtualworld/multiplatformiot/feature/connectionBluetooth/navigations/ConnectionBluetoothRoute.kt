package com.wirtualworld.multiplatformiot.feature.connectionBluetooth.navigations

import androidx.compose.ui.unit.Dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen.ConnectionBluetoothScreen

fun NavGraphBuilder.connectionBluetoothGraph(
    navController: NavHostController,
    //valueScroll: (Dp) -> Unit
) {

    composable(ConnectionBluetoothNavigation.ConnectionBluetooth.route) {

        //val viewModel: ConectionInternetViewModel = koinViewModel()

        ConnectionBluetoothScreen(
            popBackStack = { navController.popBackStack() },

//            goToDetailArduino = { arduinoName ->
//                navController.navigate(ConectionInternetNavigation.DetailArduino(arduinoName).createRoute( arduinoName ))
//            },
//            goToAddArduino = {
//                navController.navigate(ConectionInternetNavigation.AddArduino.route)
//            },

            // valueScroll = valueScroll,


            // viewModel = viewModel,
        )
    }

}