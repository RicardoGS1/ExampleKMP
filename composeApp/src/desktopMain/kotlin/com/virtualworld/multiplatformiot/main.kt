package com.virtualworld.multiplatformiot

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.virtualworld.multiplatformiot.id.initKoin

fun main() = application {

    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "MultiplatformIoT",
    ) {
        App()
    }
}