package com.virtualworld.multiplatformiot.ui.core

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.virtualworld.multiplatformiot.ui.core.theme.MyColorScheme
import com.virtualworld.multiplatformiot.ui.core.theme.MyIconSize
import com.virtualworld.multiplatformiot.ui.core.theme.MyPadding
import com.virtualworld.multiplatformiot.ui.core.theme.MyShape
import com.virtualworld.multiplatformiot.ui.core.theme.MyTypography
import com.virtualworld.multiplatformiot.ui.core.theme.colorScheme
import com.virtualworld.multiplatformiot.ui.core.theme.iconSize
import com.virtualworld.multiplatformiot.ui.core.theme.localMyAppColorScheme
import com.virtualworld.multiplatformiot.ui.core.theme.localMyAppIconSize
import com.virtualworld.multiplatformiot.ui.core.theme.localMyAppPadding
import com.virtualworld.multiplatformiot.ui.core.theme.localMyAppShape
import com.virtualworld.multiplatformiot.ui.core.theme.localMyAppTypography
import com.virtualworld.multiplatformiot.ui.core.theme.padding
import com.virtualworld.multiplatformiot.ui.core.theme.shape
import com.virtualworld.multiplatformiot.ui.core.theme.typography


@Composable
fun MyAppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val rippleIndication = ripple()

    @Suppress("DEPRECATION_ERROR")
    CompositionLocalProvider(

        localMyAppColorScheme provides colorScheme(isDarkTheme),

        localMyAppTypography provides typography(),

        localMyAppIconSize provides iconSize,

        localMyAppPadding provides padding,

        localMyAppShape provides shape,

        LocalIndication provides rippleIndication,

        content = content

    )
}

object MyAppTheme {

    val typography: MyTypography
        @Composable
        get() = localMyAppTypography.current

    val colorScheme: MyColorScheme
        @Composable
        get() = localMyAppColorScheme.current

    val iconSize: MyIconSize
        @Composable
        get() = localMyAppIconSize.current

    val padding: MyPadding
        @Composable
        get() = localMyAppPadding.current

    val shape: MyShape
        @Composable
        get() = localMyAppShape.current
}