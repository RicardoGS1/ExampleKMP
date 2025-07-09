package com.virtualworld.multiplatformiot.ui.core.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme

@Composable
fun TopBarCanva(
    arcEnable: Boolean,
    modifier: Modifier = Modifier,
) {


    val canvasSize = 300.dp
    val color = MyAppTheme.colorScheme.primary
    var animar by remember { mutableStateOf(arcEnable) }


    val arcoAlturaAnimada by animateFloatAsState(

        targetValue = if (animar) 0.0f else 0.3f,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)

    )

    LaunchedEffect(Unit) {
        animar = !animar
    }


    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(canvasSize)
    ) {

        // Crea el degradado
        val brush = Brush.horizontalGradient(
            colors = listOf(

                color.copy(alpha = 0.6f),
                color
            ),
            startX = 0f,
            endX = size.width
        )


        val rectWidth = size.width
        val rectHeight = size.height * 0.7f
        val arcoAltura = size.height * arcoAlturaAnimada

        drawRect(
            brush = brush,
            topLeft = Offset(0f, 0f),
            size = androidx.compose.ui.geometry.Size(rectWidth, rectHeight)
        )

        drawArc(
            brush = brush,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(0f, rectHeight - arcoAltura / 2),
            size = Size(rectWidth, arcoAltura)
        )

    }

}