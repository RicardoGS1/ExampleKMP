package com.virtualworld.multiplatformiot.feature.conectionInternet.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.virtualworld.multiplatformiot.feature.conectionInternet.navigations.ConectionInternetNavigation.Companion.ARDUINO_NAME_ARG
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.ConectionInternetScreen
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.ConectionInternetViewModel
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.DetailArduinoScreen
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.DetailArduinoViewModel
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.AddArduinoScreen
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.AddArduinoViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.conectionInternetGraph(
    navController: NavHostController
) {

    composable<ConectionInternetNavigation.ConectionInternet> {

        val viewModel: ConectionInternetViewModel = koinViewModel()

        ConectionInternetScreen(
            popBackStack = { navController.popBackStack() },

            goToDetailArduino = { arduinoName ->
                navController.navigate(ConectionInternetNavigation.DetailArduino(arduinoName).createRoute(arduinoName))
            },
            goToAddArduino = {
                navController.navigate(ConectionInternetNavigation.AddArduino.route)
            },

            viewModel = viewModel,
        )
    }

    composable(
        route = ConectionInternetNavigation.DetailArduino("").route,
        arguments = listOf(
            navArgument(ARDUINO_NAME_ARG) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val arduinoName = backStackEntry.arguments?.getString(ARDUINO_NAME_ARG) ?: ""
        val viewModel: DetailArduinoViewModel = koinViewModel()
        DetailArduinoScreen(
            arduinoName = arduinoName,
            popBackStack = { navController.popBackStack() },
            viewModel = viewModel
        )
    }

    composable(
        route = ConectionInternetNavigation.AddArduino.route) {
        val viewModel: AddArduinoViewModel = koinViewModel()
        AddArduinoScreen(
            popBackStack = { navController.popBackStack() },
            viewModel = viewModel
        )
    }

//    composable<ConectionInternetNavigation.AddArduino> {
//        val viewModel: AddArduinoViewModel = koinViewModel()
//        AddArduinoScreen(
//            popBackStack = { navController.popBackStack() },
//            viewModel = viewModel
//        )
//    }
}

