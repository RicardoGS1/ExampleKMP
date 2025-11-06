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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme

/**
 * Un Composable que dibuja una barra superior (TopBar) personalizada con un rectángulo y un arco,
 * ambos con alturas y animaciones configurables. Ideal para cabeceras de pantalla dinámicas.
 *
 * @param defaultArcSize El tamaño (altura) objetivo al que el arco se animará.
 * @param defaultRectSize El tamaño (altura) objetivo al que el rectángulo se animará.
 * @param animateRec Determina si la animación del cambio de tamaño del rectángulo debe ejecutarse. Si es `false`, el cambio de tamaño es instantáneo (duración 0).
 */

@Composable
fun TopBarCanva(
    defaultArcSize: Dp,
    defaultRectSize: Dp,
    animateRec: Boolean = true,
    ) {

    val color = MyAppTheme.colorScheme.primary

    val brush = remember(color) {
        Brush.horizontalGradient(
            colors = listOf(Color.White, color),
            startX = -1000f,
            //endX = 200f // Considera si este valor debería ser dinámico basado en el ancho
        )
    }

    val durationMillis = if (animateRec) 1500 else 0


    val sizeRectAnimated by animateFloatAsState(
        targetValue = defaultRectSize.value,
        animationSpec = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing)
    )

    val sizeArcAnimated by animateFloatAsState(
        targetValue = defaultArcSize.value,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
    )


    Box() {

        Box(modifier = Modifier.fillMaxWidth().height(sizeRectAnimated.dp).background(brush)) {}

        Canvas(
            modifier = Modifier.fillMaxWidth().padding(top = sizeRectAnimated.dp - 3.dp - sizeArcAnimated.dp / 2).height(sizeArcAnimated.dp)
        ) {
            val rectHeight = size.height
            val rectWidth = size.width

            drawArc(
                brush = brush,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(0f, 0f),
                size = Size(rectWidth, rectHeight)
            )
        }
    }
}