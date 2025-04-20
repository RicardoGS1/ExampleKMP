package com.virtualworld.multiplatformiot.ui.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

data class MyShape(
    val container: Shape,
    val button: Shape
)

val localMyAppShape = staticCompositionLocalOf {

    MyShape(
        container = RectangleShape,
        button = RectangleShape
    )
}

val shape = MyShape(

    container = RectangleShape,
    button = RoundedCornerShape(8.dp)
)