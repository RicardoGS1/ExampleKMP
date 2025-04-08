package com.wirtualworld.multiplatformiot.feature.conectionLocal.navigations

import kotlinx.serialization.Serializable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wirtualworld.multiplatformiot.feature.conectionLocal.screen.ConectionLocalScreen
import com.wirtualworld.multiplatformiot.feature.conectionLocal.screen.ConectionLocalViewModel
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object ConectionLocal


fun NavGraphBuilder.conectionLocalRoute(){

    composable <ConectionLocal> {

        val viewModel: ConectionLocalViewModel = koinViewModel()

        ConectionLocalScreen(
            viewModel = viewModel
        )

    }

}