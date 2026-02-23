package com.virtualworld.multiplatformiot.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.waterfall
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.virtualworld.multiplatformiot.feature.conectionInternet.navigations.ConectionInternetNavigation
import com.virtualworld.multiplatformiot.feature.login.AuthStateViewModel
import com.virtualworld.multiplatformiot.feature.login.navigations.LoginNavigation
import com.virtualworld.multiplatformiot.feature.menu.navigations.MenuNavigation
import com.virtualworld.multiplatformiot.feature.profile.navigations.ProfileNavigation
import com.virtualworld.multiplatformiot.navigation.AppNavHost
import com.virtualworld.multiplatformiot.ui.core.component.ARC_NULL
import com.virtualworld.multiplatformiot.ui.core.component.ARC_HEIGHT
import com.virtualworld.multiplatformiot.ui.core.component.RECT_HMEDIUM
import com.virtualworld.multiplatformiot.ui.core.component.RECT_HEIGHT
import com.virtualworld.multiplatformiot.ui.core.component.RECT_SMALL
import com.virtualworld.multiplatformiot.ui.core.component.TopBarCanva
import com.wirtualworld.multiplatformiot.feature.conectionLocal.navigations.ConectionLocalNavigation
import com.wirtualworld.multiplatformiot.feature.connectionBLE.navigations.ConnectionBLENavigation
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.navigations.ConnectionBluetoothNavigation
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    showGoogleSignIn: Boolean = true,
    onGoogleSignInRequest: ((String) -> Unit) -> Unit = {},
) {

    val navController = rememberNavController()
    val authStateViewModel: AuthStateViewModel = koinViewModel()
    val user by authStateViewModel.authState.collectAsState()
    val isReady by authStateViewModel.isReady.collectAsState()


    val paddingSinBarValues = WindowInsets.waterfall.asPaddingValues()


    var defaultArcSize by remember { mutableStateOf(ARC_NULL) }
    var defaultRectSize by remember { mutableStateOf(RECT_SMALL) }

    var animateRect by remember { mutableStateOf(true) }

    val valueScroll = { valueSize: Dp ->
        defaultRectSize = valueSize.value
        if (valueSize != RECT_HMEDIUM.dp)
            animateRect = false
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val startDestination = remember { mutableStateOf<String?>(null) }


    // 2. Efecto para decidir el destino INICIAL
    LaunchedEffect(isReady, user) {
        if (isReady) {
            startDestination.value = if (user == null) {
                LoginNavigation.Login.route
            } else {
                MenuNavigation.Menu.route
            }
        }
    }

    // Al cerrar sesión, navegar al login y limpiar el back stack
    LaunchedEffect(user, isReady, currentRoute) {
        if (isReady && user == null && currentRoute != null &&
            currentRoute != LoginNavigation.Login.route && currentRoute != LoginNavigation.Register.route
        ) {
            startDestination.value?.let { startRoute ->
                navController.navigate(LoginNavigation.Login.route) {
                    popUpTo(startRoute) { inclusive = true }
                }
            }
        }
    }

    //LaunchedEffect(currentRoute) {

        if (currentRoute != null) {


            when (currentRoute) {

                LoginNavigation.Login.route, LoginNavigation.Register.route -> {
                    animateRect = true
                    defaultArcSize = ARC_NULL
                    defaultRectSize = RECT_HEIGHT
                }

                MenuNavigation.Menu.route -> {
                    animateRect = true
                    defaultArcSize = ARC_HEIGHT
                    defaultRectSize = RECT_HEIGHT
                }

                ConectionInternetNavigation.ConectionInternet.route -> {
                    animateRect = true
                    defaultArcSize = ARC_NULL
                }

                ConnectionBluetoothNavigation.ConnectionBluetooth.route -> {
                    animateRect = true
                    defaultArcSize = ARC_NULL
                }

                ConnectionBLENavigation.ConnectionBluetoothLE.route -> {
                    animateRect = true
                    defaultArcSize = ARC_NULL
                }

                ConectionLocalNavigation.ConectionLocal.route -> {
                    animateRect = true
                    defaultArcSize = ARC_NULL
                }

                ProfileNavigation.Profile.route -> {
                    animateRect = true
                    defaultArcSize = ARC_NULL
                }

                ConectionInternetNavigation.DetailArduino("").route -> {
                    animateRect = true

                    defaultArcSize = ARC_NULL
                    defaultRectSize = RECT_HEIGHT
                }

                ConnectionBluetoothNavigation.DetailArduino("", "").route -> {
                    animateRect = true
                    defaultArcSize = ARC_NULL
                    defaultRectSize = RECT_HEIGHT
                }

                ConnectionBLENavigation.DetailArduino("", "").route -> {
                    animateRect = true
                    defaultArcSize = ARC_NULL
                    defaultRectSize = RECT_HEIGHT
                }
            }
        }

    //}

    Surface(modifier = Modifier.fillMaxSize()) {

        if (startDestination.value.isNullOrEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {

            TopBarCanva(defaultArcSize.dp, defaultRectSize.dp, animateRect)


            AppNavHost(
                navController = navController,
                startDestination = startDestination.value!!,
                paddingValues = paddingSinBarValues,
                valueScroll = valueScroll,
                showGoogleSignIn = showGoogleSignIn,
                onGoogleSignInRequest = onGoogleSignInRequest,
            )


        }

    }




}