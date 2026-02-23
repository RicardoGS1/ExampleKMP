package com.virtualworld.multiplatformiot.ui.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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

        //TITLE
        titleLarge = TextStyle(
            fontFamily = FontFamily(
                Font(
                    Res.font.my_font_primary,
                    FontWeight.Normal
                )
            ),
            fontSize = 16.sp

        ),
        titleMedium = TextStyle(
            fontFamily = FontFamily(
                Font(
                    Res.font.my_font_primary,
                    FontWeight.Normal
                )
            ),
            fontSize = 14.sp

        ),
        titleSmall = TextStyle(
            fontFamily = FontFamily(
                Font(
                    Res.font.my_font_primary,
                    FontWeight.Normal
                )
            ),
            fontSize = 12.sp

        ),

        //BODY
        bodyLarge = TextStyle(
            fontFamily = FontFamily(
                Font(
                    Res.font.my_font_primary,
                    FontWeight.Normal
                )
            ),
            lineHeight = 18.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = FontFamily(
                Font(
                    Res.font.my_font_primary,
                    FontWeight.Normal
                )
            ),
            lineHeight = 20.sp
        ),
        bodySmall = TextStyle(
            fontFamily = FontFamily(
                Font(
                    Res.font.my_font_primary,
                    FontWeight.Normal
                )
            ),
            lineHeight = 22.sp
        ),

        //LABEL
        labelLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 18.sp
        ),
        labelMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 14.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 12.sp
        ),
    )
