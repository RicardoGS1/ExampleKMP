package com.virtualworld.multiplatformiot.feature.menu.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.virtualworld.multiplatformiot.feature.menu.MenuScreen
import com.virtualworld.multiplatformiot.feature.menu.MenuViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object Menu


fun NavGraphBuilder.menuRoutes(
    goToLocalConection: () -> Unit,
    goToInternetConection: () -> Unit,
    goToBluetoothConection: () -> Unit,) {

    composable <Menu> {

        val viewModel: MenuViewModel = koinViewModel()
        
        MenuScreen(
            goToLocalConection = goToLocalConection,
            goToInternetConection=goToInternetConection,
            goToBluetoothConection=goToBluetoothConection,
            menuViewModel = viewModel
        )

    }

}

