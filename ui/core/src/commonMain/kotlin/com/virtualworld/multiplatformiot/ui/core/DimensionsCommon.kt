package com.virtualworld.multiplatformiot.ui.core

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DimensionsCommon(
    val iconTiny : Dp = 40.dp,
    val iconSmall : Dp = 48.dp,
    val iconNormal : Dp = 56.dp,
    val iconBig : Dp = 64.dp,
    val iconLarge : Dp = 72.dp
)


val localDimensionsCommon = compositionLocalOf { DimensionsCommon() }