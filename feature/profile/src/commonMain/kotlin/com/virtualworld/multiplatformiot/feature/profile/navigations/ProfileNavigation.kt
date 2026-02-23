package com.virtualworld.multiplatformiot.feature.profile.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.virtualworld.multiplatformiot.feature.profile.ProfileScreen
import com.virtualworld.multiplatformiot.feature.profile.ProfileViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
sealed class ProfileNavigation(val route: String) {

    @Serializable
    data object Profile : ProfileNavigation("profile")
}

fun NavGraphBuilder.profileRoutesGraph(
    onNavigateBack: () -> Unit,
) {
    composable(ProfileNavigation.Profile.route) {
        val viewModel: ProfileViewModel = koinViewModel()
        ProfileScreen(
            viewModel = viewModel,
            onNavigateBack = onNavigateBack,
        )
    }
}
