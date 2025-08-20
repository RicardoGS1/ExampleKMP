package com.virtualworld.multiplatformiot.ui.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


val _Bluetooth = ImageVector.Builder(
    name = "Bluetooth",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 960f,
    viewportHeight = 960f
).apply {
    path(
        fill = SolidColor(Color(0xFF000000))
    ) {
        moveTo(440f, 880f)
        verticalLineToRelative(-304f)
        lineTo(256f, 760f)
        lineToRelative(-56f, -56f)
        lineToRelative(224f, -224f)
        lineToRelative(-224f, -224f)
        lineToRelative(56f, -56f)
        lineToRelative(184f, 184f)
        verticalLineToRelative(-304f)
        horizontalLineToRelative(40f)
        lineToRelative(228f, 228f)
        lineToRelative(-172f, 172f)
        lineToRelative(172f, 172f)
        lineTo(480f, 880f)
        close()
        moveToRelative(80f, -496f)
        lineToRelative(76f, -76f)
        lineToRelative(-76f, -74f)
        close()
        moveToRelative(0f, 342f)
        lineToRelative(76f, -74f)
        lineToRelative(-76f, -76f)
        close()
    }
}.build()


val signal_cellular_alt = ImageVector.Builder(
    name = "Signal_cellular_alt",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 960f,
    viewportHeight = 960f
).apply {
    path(
        fill = SolidColor(Color(0xFF000000))
    ) {
        moveTo(200f, 800f)
        verticalLineToRelative(-240f)
        horizontalLineToRelative(120f)
        verticalLineToRelative(240f)
        close()
        moveToRelative(240f, 0f)
        verticalLineToRelative(-440f)
        horizontalLineToRelative(120f)
        verticalLineToRelative(440f)
        close()
        moveToRelative(240f, 0f)
        verticalLineToRelative(-640f)
        horizontalLineToRelative(120f)
        verticalLineToRelative(640f)
        close()
    }
}.build()


val info = ImageVector.Builder(
    name = "Info",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 960f,
    viewportHeight = 960f
).apply {
    path(
        fill = SolidColor(Color(0xFF000000))
    ) {
        moveTo(440f, 680f)
        horizontalLineToRelative(80f)
        verticalLineToRelative(-240f)
        horizontalLineToRelative(-80f)
        close()
        moveToRelative(40f, -320f)
        quadToRelative(17f, 0f, 28.5f, -11.5f)
        reflectiveQuadTo(520f, 320f)
        reflectiveQuadToRelative(-11.5f, -28.5f)
        reflectiveQuadTo(480f, 280f)
        reflectiveQuadToRelative(-28.5f, 11.5f)
        reflectiveQuadTo(440f, 320f)
        reflectiveQuadToRelative(11.5f, 28.5f)
        reflectiveQuadTo(480f, 360f)
        moveToRelative(0f, 520f)
        quadToRelative(-83f, 0f, -156f, -31.5f)
        reflectiveQuadTo(197f, 763f)
        reflectiveQuadToRelative(-85.5f, -127f)
        reflectiveQuadTo(80f, 480f)
        reflectiveQuadToRelative(31.5f, -156f)
        reflectiveQuadTo(197f, 197f)
        reflectiveQuadToRelative(127f, -85.5f)
        reflectiveQuadTo(480f, 80f)
        reflectiveQuadToRelative(156f, 31.5f)
        reflectiveQuadTo(763f, 197f)
        reflectiveQuadToRelative(85.5f, 127f)
        reflectiveQuadTo(880f, 480f)
        reflectiveQuadToRelative(-31.5f, 156f)
        reflectiveQuadTo(763f, 763f)
        reflectiveQuadToRelative(-127f, 85.5f)
        reflectiveQuadTo(480f, 880f)
        moveToRelative(0f, -80f)
        quadToRelative(134f, 0f, 227f, -93f)
        reflectiveQuadToRelative(93f, -227f)
        reflectiveQuadToRelative(-93f, -227f)
        reflectiveQuadToRelative(-227f, -93f)
        reflectiveQuadToRelative(-227f, 93f)
        reflectiveQuadToRelative(-93f, 227f)
        reflectiveQuadToRelative(93f, 227f)
        reflectiveQuadToRelative(227f, 93f)
        moveToRelative(0f, -320f)
    }
}.build()


val person = ImageVector.Builder(
    name = "Person",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 960f,
    viewportHeight = 960f
).apply {
    path(
        fill = SolidColor(Color(0xFF000000))
    ) {
        moveTo(480f, 480f)
        quadToRelative(-66f, 0f, -113f, -47f)
        reflectiveQuadToRelative(-47f, -113f)
        reflectiveQuadToRelative(47f, -113f)
        reflectiveQuadToRelative(113f, -47f)
        reflectiveQuadToRelative(113f, 47f)
        reflectiveQuadToRelative(47f, 113f)
        reflectiveQuadToRelative(-47f, 113f)
        reflectiveQuadToRelative(-113f, 47f)
        moveTo(160f, 800f)
        verticalLineToRelative(-112f)
        quadToRelative(0f, -34f, 17.5f, -62.5f)
        reflectiveQuadTo(224f, 582f)
        quadToRelative(62f, -31f, 126f, -46.5f)
        reflectiveQuadTo(480f, 520f)
        reflectiveQuadToRelative(130f, 15.5f)
        reflectiveQuadTo(736f, 582f)
        quadToRelative(29f, 15f, 46.5f, 43.5f)
        reflectiveQuadTo(800f, 688f)
        verticalLineToRelative(112f)
        close()
        moveToRelative(80f, -80f)
        horizontalLineToRelative(480f)
        verticalLineToRelative(-32f)
        quadToRelative(0f, -11f, -5.5f, -20f)
        reflectiveQuadTo(700f, 654f)
        quadToRelative(-54f, -27f, -109f, -40.5f)
        reflectiveQuadTo(480f, 600f)
        reflectiveQuadToRelative(-111f, 13.5f)
        reflectiveQuadTo(260f, 654f)
        quadToRelative(-9f, 5f, -14.5f, 14f)
        reflectiveQuadToRelative(-5.5f, 20f)
        close()
        moveToRelative(240f, -320f)
        quadToRelative(33f, 0f, 56.5f, -23.5f)
        reflectiveQuadTo(560f, 320f)
        reflectiveQuadToRelative(-23.5f, -56.5f)
        reflectiveQuadTo(480f, 240f)
        reflectiveQuadToRelative(-56.5f, 23.5f)
        reflectiveQuadTo(400f, 320f)
        reflectiveQuadToRelative(23.5f, 56.5f)
        reflectiveQuadTo(480f, 400f)
        moveToRelative(0f, 320f)
    }
}.build()


val waveHaikei = ImageVector.Builder(
    name = "waveHaikei",
    defaultWidth = 900.dp,
    defaultHeight = 600.dp,
    viewportWidth = 900f,
    viewportHeight = 600f
).apply {
    path(
        fill = SolidColor(Color(0xFF0066FF))
    ) {
        moveTo(0f, 264f)

        lineTo(75f, 298.7f)

        curveTo(150f, 333.3f, 300f, 402.7f, 450f, 414.3f)
        curveTo(600f, 426f, 750f, 380f, 825f, 357f)

        lineTo(900f, 334f)
        lineTo(900f, 0f)
        lineTo(825f, 0f)
        curveTo(750f, 0f, 600f, 0f, 450f, 0f)
        curveTo(300f, 0f, 150f, 0f, 75f, 0f)
        lineTo(0f, 0f)
        close()
    }
}.build()


data class MyIcons(

    val bluetooth: ImageVector,
    val internet: ImageVector,
    val info: ImageVector,
    val person: ImageVector,
    val waveHaikei: ImageVector


)

val localMyAppIcons = staticCompositionLocalOf {
    MyIcons(
        bluetooth = Icons.Filled.Settings,
        internet = Icons.Default.Settings,
        info = Icons.Default.Settings,
        person = Icons.Default.Settings,
        waveHaikei = Icons.Default.Settings,
    )
}

val myIconsDefault = MyIcons(
    bluetooth = _Bluetooth,
    internet = signal_cellular_alt,
    info = info,
    person = person,
    waveHaikei = waveHaikei,
)




