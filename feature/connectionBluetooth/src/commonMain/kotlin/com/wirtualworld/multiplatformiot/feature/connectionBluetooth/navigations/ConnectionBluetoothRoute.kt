package com.wirtualworld.multiplatformiot.feature.connectionBluetooth.navigations

import androidx.compose.ui.unit.Dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.navigations.ConnectionBluetoothNavigation.Companion.ARDUINO_ADDRESS_ARG
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.navigations.ConnectionBluetoothNavigation.Companion.ARDUINO_NAME_ARG
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen.ConnectionBluetoothScreen
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen.ConnectionBluetoothViewModel
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen.DetailArduinoScreenu
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen.DetailArduinoViewModelB
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.connectionBluetoothGraph(
    navController: NavHostController,
    valueScroll: (Dp) -> Unit
) {

    composable(ConnectionBluetoothNavigation.ConnectionBluetooth.route) {

        val viewModel: ConnectionBluetoothViewModel = koinViewModel()

        ConnectionBluetoothScreen(
            popBackStack = { navController.popBackStack() },
            goToDetailArduino = { arduinoName, arduinoAddress ->
                navController.navigate(ConnectionBluetoothNavigation.DetailArduino(arduinoName,arduinoAddress).createRoute(arduinoName,arduinoAddress))
            },
            goToAddArduino = { },//navController.navigate(ConnectionBluetoothNavigation.AddArduino.route)
            viewModel = viewModel,
            valueScroll = valueScroll

        )
    }


    composable(
        route = ConnectionBluetoothNavigation.DetailArduino("","").route,
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


        val viewModel: DetailArduinoViewModelB = koinViewModel()
        DetailArduinoScreenu(
            arduinoName = arduinoName,
            arduinoAddress = arduinoAddress,
            popBackStack = { navController.popBackStack() },
            viewModel = viewModel
        )
    }

}