package com.virtualworld.multiplatformiot.ui.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


//Size
data class MyIconSize(
    val tiny: Dp,
    val small: Dp,
    val normal: Dp,
    val big: Dp,
    val large: Dp
)

val localMyAppIconSize = staticCompositionLocalOf {
    MyIconSize(
        tiny = Dp.Unspecified,
        small = Dp.Unspecified,
        normal = Dp.Unspecified,
        big = Dp.Unspecified,
        large = Dp.Unspecified
    )
}

val iconSize = MyIconSize(
    tiny = 40.dp,
    small = 48.dp,
    normal = 56.dp,
    big = 64.dp,
    large = 72.dp
)
