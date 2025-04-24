package com.virtualworld.multiplatformiot.feature.conectionInternet.navigations

import kotlinx.serialization.Serializable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.ConectionInternetScreen
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.ConectionInternetViewModel
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object ConectionInternet


fun NavGraphBuilder.conectionInternetRoute(popBackStack: ()-> Unit) {

    composable <ConectionInternet> {

        val viewModel: ConectionInternetViewModel = koinViewModel()

        ConectionInternetScreen(
            viewModel = viewModel,popBackStack
        )

    }

}