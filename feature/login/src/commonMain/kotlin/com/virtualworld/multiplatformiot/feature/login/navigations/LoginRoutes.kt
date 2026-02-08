package com.virtualworld.multiplatformiot.feature.login.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.virtualworld.multiplatformiot.feature.login.LoginScreen
import com.virtualworld.multiplatformiot.feature.login.RegisterScreen
import com.virtualworld.multiplatformiot.feature.login.LoginViewModel
import com.virtualworld.multiplatformiot.feature.login.RegisterViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
sealed class LoginNavigation(val route: String) {

    @Serializable
    data object Login : LoginNavigation("login")
    @Serializable
    data object Register : LoginNavigation("register")
}

fun NavGraphBuilder.loginRoutesGraph(
    onNavigateToRegister: () -> Unit,
    onNavigateToMenu: () -> Unit,
    onNavigateBack: () -> Unit = {},
    showGoogleSignIn: Boolean = true,
    onGoogleSignInRequest: ((String) -> Unit) -> Unit = {},
) {
    composable(LoginNavigation.Login.route) {
        val viewModel: LoginViewModel = koinViewModel()
        LoginScreen(
            viewModel = viewModel,
            onNavigateToRegister = onNavigateToRegister,
            onAuthSuccess = onNavigateToMenu,
            showGoogleSignIn = showGoogleSignIn,
            onGoogleSignInRequest = onGoogleSignInRequest,
        )
    }
    composable(LoginNavigation.Register.route) {
        val viewModel: RegisterViewModel = koinViewModel()
        RegisterScreen(
            viewModel = viewModel,
            onNavigateToLogin = onNavigateBack,
            onAuthSuccess = onNavigateToMenu,
            showGoogleSignIn = showGoogleSignIn,
            onGoogleSignInRequest = onGoogleSignInRequest,
        )
    }
}
