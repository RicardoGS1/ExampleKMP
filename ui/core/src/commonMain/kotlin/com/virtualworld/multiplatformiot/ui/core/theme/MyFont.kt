package com.virtualworld.multiplatformiot.ui.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import multiplatformiot.ui.core.generated.resources.Res
import multiplatformiot.ui.core.generated.resources.my_font_primary
import org.jetbrains.compose.resources.Font

//Typography
data class MyTypography(
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle
)


val localMyAppTypography = staticCompositionLocalOf {
    MyTypography(
        titleLarge = TextStyle.Default,
        titleMedium = TextStyle.Default,
        titleSmall = TextStyle.Default,
        bodyLarge = TextStyle.Default,
        bodyMedium = TextStyle.Default,
        bodySmall = TextStyle.Default,
        labelLarge = TextStyle.Default,
        labelMedium = TextStyle.Default,
        labelSmall = TextStyle.Default
    )
}

@Composable
fun typography() =

    MyTypography(
        titleLarge = TextStyle(
            fontFamily = FontFamily(
                Font(
                    Res.font.my_font_primary,
                    FontWeight.Normal
                )
            )
        ),
        titleMedium = TextStyle(
            fontFamily = FontFamily(
                Font(
                    Res.font.my_font_primary,
                    FontWeight.Normal
                )
            )
        ),
        titleSmall = TextStyle(
            fontFamily = FontFamily(
                Font(
                    Res.font.my_font_primary,
                    FontWeight.Normal
                )
            )
        ),
        bodyLarge = TextStyle(
            fontFamily = FontFamily(
                Font(
                    Res.font.my_font_primary,
                    FontWeight.Normal
                )
            )
        ),
        bodyMedium = TextStyle(
            fontFamily = FontFamily(
                Font(
                    Res.font.my_font_primary,
                    FontWeight.Normal
                )
            )
        ),
        bodySmall = TextStyle(
            fontFamily = FontFamily(
                Font(
                    Res.font.my_font_primary,
                    FontWeight.Normal
                )
            )
        ),
        labelLarge = TextStyle.Default,
        labelMedium = TextStyle.Default,
        labelSmall = TextStyle.Default,
    )
