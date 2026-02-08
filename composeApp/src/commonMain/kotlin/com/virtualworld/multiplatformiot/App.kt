package com.virtualworld.multiplatformiot

import androidx.compose.runtime.Composable
import com.virtualworld.multiplatformiot.id.appModule
import com.virtualworld.multiplatformiot.ui.MainScreen
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.module.Module


@Composable
@Preview
fun App(
    platformModule: Module = Module(),
    onGoogleSignInRequest: ((String) -> Unit) -> Unit = {},
) {

    KoinApplication(
        application = {
            modules(appModule, platformModule)
        }
    ) {

        MyAppTheme {

            MainScreen(onGoogleSignInRequest = onGoogleSignInRequest)

        }
    }

}