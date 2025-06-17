package com.virtualworld.multiplatformiot.ui.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class MyPadding(
    val tiny: Dp,
    val small: Dp,
    val normal: Dp,
    val big: Dp,
    val large: Dp
)

val localMyAppPadding = staticCompositionLocalOf {

    MyPadding(
        tiny = Dp.Unspecified,
        small = Dp.Unspecified,
        normal = Dp.Unspecified,
        big = Dp.Unspecified,
        large = Dp.Unspecified
    )
}

val padding = MyPadding(
    tiny = 8.dp,
    small = 12.dp,
    normal = 16.dp,
    big = 20.dp,
    large = 24.dp
)