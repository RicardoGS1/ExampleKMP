package com.wirtualworld.multiplatformiot.feature.connectionBLE.navigations

import androidx.compose.ui.unit.Dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wirtualworld.multiplatformiot.feature.connectionBLE.navigations.ConnectionBLENavigation.Companion.ARDUINO_ADDRESS_ARG
import com.wirtualworld.multiplatformiot.feature.connectionBLE.navigations.ConnectionBLENavigation.Companion.ARDUINO_NAME_ARG
import com.wirtualworld.multiplatformiot.feature.connectionBLE.screen.ConnectionBLEScreen
import com.wirtualworld.multiplatformiot.feature.connectionBLE.screen.ConnectionBLEViewModel
import com.wirtualworld.multiplatformiot.feature.connectionBLE.screen.DetailArduinoBLEScreen
import com.wirtualworld.multiplatformiot.feature.connectionBLE.screen.DetailArduinoBLEViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.connectionBLEGraph(
    navController: NavHostController,
    valueScroll: (Dp) -> Unit
) {


    composable(ConnectionBLENavigation.ConnectionBluetoothLE.route) {

        val viewModel: ConnectionBLEViewModel = koinViewModel()

        ConnectionBLEScreen(
            popBackStack = { navController.popBackStack() },
            goToDetailArduino = { arduinoName, arduinoAddress ->
                navController.navigate(ConnectionBLENavigation.DetailArduino(arduinoName,arduinoAddress).createRoute(arduinoName,arduinoAddress))
            },
            viewModel = viewModel,
            valueScroll = valueScroll

        )
    }


    composable(
        route = ConnectionBLENavigation.DetailArduino("","").route,
        arguments = listOf(
            navArgument(ARDUINO_NAME_ARG) {
                type = NavType.StringType
            },
            navArgument(ARDUINO_ADDRESS_ARG) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val arduinoName = backStackEntry.arguments?.getString(ARDUINO_NAME_ARG) ?: ""
        val arduinoAddress = backStackEntry.arguments?.getString(ARDUINO_ADDRESS_ARG) ?: ""


        val viewModel: DetailArduinoBLEViewModel = koinViewModel()
        DetailArduinoBLEScreen(
            arduinoName = arduinoName,
            arduinoAddress = arduinoAddress,
            popBackStack = { navController.popBackStack() },
            viewModel = viewModel
        )
    }

}