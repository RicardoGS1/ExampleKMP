package com.virtualworld.multiplatformiot

import androidx.compose.ui.window.ComposeUIViewController
import com.virtualworld.multiplatformiot.id.initKoin

fun MainViewController() = ComposeUIViewController(configure = {

    initKoin()
}
) {


    App()


}