package com.wirtualworld.multiplatformiot.feature.conectionLocal.navigations

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.Serializable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wirtualworld.multiplatformiot.feature.conectionLocal.screen.ConectionLocalScreen
import com.wirtualworld.multiplatformiot.feature.conectionLocal.screen.ConectionLocalViewModel
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object ConectionLocal


fun NavGraphBuilder.conectionLocalRoute(popBackStack: () -> Unit, valueScroll: (Dp) -> Unit) {

    composable(ConectionLocalNavigation.ConectionLocal.route) {

        val viewModel: ConectionLocalViewModel = koinViewModel()

        ConectionLocalScreen(
            viewModel = viewModel, popBackStack, valueScroll = valueScroll
        )

    }

}