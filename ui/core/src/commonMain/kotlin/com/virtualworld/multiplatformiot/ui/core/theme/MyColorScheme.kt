package com.virtualworld.multiplatformiot.ui.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


// Vibrant Blues
val vibrantBlue = Color( 0xFF8B6DE9)        // Vibrant Blue
val white = Color(0xFFFFFFFF)             // White
val lightBlue = Color(0xFFE0EEFF)          // Light Blue variant
val darkBlue = Color(0xFF00244D)           // Dark Blue

// Vibrant Oranges
val vibrantOrange = Color(0xFF007BFF)      // Vibrant Orange
val black = Color(0xFF000000)


data class MyColorScheme(

    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,

    )

val localMyAppColorScheme = staticCompositionLocalOf {
    MyColorScheme(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        primaryContainer = Color.Unspecified,
        onPrimaryContainer = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified
    )
}


val MyLightColorScheme = MyColorScheme(
    primary = vibrantBlue,      // Vibrant Blue
    onPrimary = white,   // White - good contrast on blue
    primaryContainer = lightBlue, // Lighter blue variant
    onPrimaryContainer = darkBlue, // Darker blue for text on the container
    secondary = vibrantOrange,    // Vibrant Orange
    onSecondary = black,  // Black - good contrast on orange
)

val MyDarkColorScheme = MyColorScheme(
    primary = Color(0xFF64B5F6),      // Lighter blue for dark theme
    onPrimary = black,   // Black - contrast on lighter blue
    primaryContainer = Color(0xFF1A237E), // Darker blue container
    onPrimaryContainer = lightBlue, // Lighter blue for text on the container
    secondary = Color(0xFFD4B781),    // Muted orange for dark theme
    onSecondary = black,  // Black - contrast on orange
)

@Composable
fun colorScheme(isDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isDarkTheme) MyDarkColorScheme else MyLightColorScheme



