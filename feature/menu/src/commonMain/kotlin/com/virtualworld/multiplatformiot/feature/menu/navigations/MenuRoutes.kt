package com.virtualworld.multiplatformiot.feature.menu.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.virtualworld.multiplatformiot.feature.menu.MenuScreen
import com.virtualworld.multiplatformiot.feature.menu.MenuViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
sealed class MenuNavigation(val route: String) {

    @Serializable
    data object Menu : MenuNavigation("menu")
}


fun NavGraphBuilder.menuRoutesGraph(
    goToLocalConection: () -> Unit,
    goToInternetConection: () -> Unit,
    goToBluetoothConection: () -> Unit,
    goToBluetoothLEConection: () -> Unit,) {

    composable(MenuNavigation.Menu.route) {

        val viewModel: MenuViewModel = koinViewModel()

        MenuScreen(
            goToLocalConection = goToLocalConection,
            goToInternetConection = goToInternetConection,
            goToBluetoothConection = goToBluetoothConection,
            goToBluetoothLEConection = goToBluetoothLEConection,
            menuViewModel = viewModel
        )

    }

}

