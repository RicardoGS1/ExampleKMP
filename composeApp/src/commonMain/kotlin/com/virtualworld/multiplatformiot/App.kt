package com.virtualworld.multiplatformiot

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.virtualworld.multiplatformiot.id.appModule
import com.virtualworld.multiplatformiot.ui.MainScreen
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.module.Module


@Composable
@Preview
fun App( platformModule: Module = Module()  ) {

    KoinApplication(
        application = {
            modules(appModule, platformModule)
        }
    ) {

        MyAppTheme {


            MainScreen()


        }
    }

}