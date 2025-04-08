package com.virtualworld.multiplatformiot.ui.core

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PaddingCommon(
    val tiny : Dp = 8.dp,
    val small : Dp = 12.dp,
    val normal : Dp = 16.dp,
    val big : Dp = 20.dp,
    val large : Dp = 24.dp
)

val localPaddingCommon = compositionLocalOf { PaddingCommon() }
