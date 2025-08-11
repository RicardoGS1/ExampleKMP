package com.virtualworld.multiplatformiot.ui.core.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme

@Composable
fun TopBarCanva(
    endArcAnimated: Float,
    endSizeRect: Dp,
    sizeArc: Dp,
    animateRec: Boolean = true,

    ) {

    val color = MyAppTheme.colorScheme.primary

    var durationAnimationRect by remember { mutableStateOf(1500) }

    durationAnimationRect = if (animateRec) 1500 else 0

    println("1")
    println("2")


    val arcoAlturaAnimada by animateFloatAsState(
        targetValue = endArcAnimated,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
    )


    val sizeRectAnimated by animateFloatAsState(
        targetValue = endSizeRect.value,
        animationSpec = tween(durationMillis = durationAnimationRect, easing = FastOutSlowInEasing)
    )


    Box() {
        val brush = Brush.horizontalGradient(
            colors = listOf(
                color.copy(alpha = 0.6f), color
            ), startX = 0f, endX = 400f
        )

        Box(modifier = Modifier.fillMaxWidth().height(sizeRectAnimated.dp).background(brush)) {}

        Canvas(
            modifier = Modifier.fillMaxWidth().padding(top = sizeRectAnimated.dp - sizeArc / 2).height(sizeArc)
        ) {
            val rectHeight = size.height
            val rectWidth = size.width

            drawArc(
                brush = brush,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(0f, (rectHeight * (1 - arcoAlturaAnimada)) / 2),
                size = Size(rectWidth, rectHeight * arcoAlturaAnimada)
            )
        }
    }
}