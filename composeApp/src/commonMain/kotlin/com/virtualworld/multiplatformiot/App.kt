package com.virtualworld.multiplatformiot

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.virtualworld.multiplatformiot.id.appModule
import com.virtualworld.multiplatformiot.ui.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module


@Composable
@Preview
fun App( platformModule: Module = Module()  ) {

    KoinApplication(
        application = {
            modules(appModule, platformModule)
        }
    ) {

        MaterialTheme {

            MainScreen()


        }
    }

}