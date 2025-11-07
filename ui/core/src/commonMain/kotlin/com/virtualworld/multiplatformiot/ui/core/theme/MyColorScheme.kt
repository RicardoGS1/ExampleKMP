package com.virtualworld.multiplatformiot.ui.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ------------------- Paleta de Colores Base -------------------
// Los nombres siguen la convención de Material Design (ej: 'seed' o color base)

// Paleta Primaria (Basada en Azul)
val primaryLight = Color(0xFF4A56E2)       // Un azul vibrante y moderno para el tema claro
val onPrimaryLight = Color(0xFFFFFFFF)     // Texto/íconos sobre el color primario
val primaryContainerLight = Color(0xFFE0E0FF) // Contenedor para elementos primarios, más suave
val onPrimaryContainerLight = Color(0xFF000D60) // Texto/íconos sobre el contenedor primario

val primaryDark = Color(0xFFBCC2FF)        // Un azul más claro y desaturado para el tema oscuro, fácil de ver
val onPrimaryDark = Color(0xFF0019A1)      // Texto/íconos sobre el primario oscuro
val primaryContainerDark = Color(0xFF233FEA) // Un azul más apagado para contenedores en tema oscuro
val onPrimaryContainerDark = Color(0xFFE0E0FF) // Texto/íconos sobre el contenedor primario oscuro

// Paleta Secundaria (Basada en Naranja/Ámbar)
val secondaryLight = Color(0xFFE96239)     // Naranja energético para acentos en tema claro
val onSecondaryLight = Color(0xFFFFFFFF)   // Texto/íconos sobre el secundario
val secondaryContainerLight = Color(0xFFFFDBCF) // Contenedor para elementos secundarios
val onSecondaryContainerLight = Color(0xFF3A0B00) // Texto/íconos sobre el contenedor secundario

val secondaryDark = Color(0xFFFFB59B)      // Naranja pastel para acentos en tema oscuro
val onSecondaryDark = Color(0xFF5F1700)    // Texto/íconos sobre el secundario oscuro
val secondaryContainerDark = Color(0xFF7E2F13)  // Contenedor para elementos secundarios en tema oscuro
val onSecondaryContainerDark = Color(0xFFFFDBCF) // Texto/íconos sobre el contenedor secundario oscuro

// Colores de Superficie y Fondo
val backgroundLight = Color(0xFFF7F2FA)   // Color de fondo principal para el tema claro (ligeramente azulado)
val onBackgroundLight = Color(0xFF1A1B21)  // Texto/contenido principal sobre el fondo
val surfaceLight = Color(0xFFFDFBFF)      // Superficie para componentes como Cards, Sheets (igual que el fondo)
val onSurfaceLight = Color(0xFF1A1B21)    // Texto/contenido sobre las superficies

val backgroundDark = Color(0xFF121214)    // Fondo principal para tema oscuro (casi negro)
val onBackgroundDark = Color(0xFFE4E2E6)   // Texto/contenido principal sobre el fondo oscuro (blanco suave)
val surfaceDark = Color(0xFF1A1B21)       // Superficie ligeramente más clara que el fondo para dar profundidad
val onSurfaceDark = Color(0xFFE4E2E6)     // Texto/contenido sobre las superficies oscuras

// ------------------- Estructura del Esquema de Color -------------------

data class MyColorScheme(
    // Roles principales (los más usados)
    val primary: Color,                // Color principal para botones, FABs y elementos activos.
    val onPrimary: Color,              // Color para texto e iconos que van encima de 'primary'.
    val primaryContainer: Color,       // Un tono más suave para contenedores que necesitan menos énfasis.
    val onPrimaryContainer: Color,     // Color para texto e iconos que van encima de 'primaryContainer'.

    // Roles secundarios (para acentos y acciones secundarias)
    val secondary: Color,              // Color para filtros, chips y otros acentos visuales.
    val onSecondary: Color,            // Color para texto e iconos encima de 'secondary'.
    val secondaryContainer: Color,     // Contenedor para elementos secundarios con menos énfasis.
    val onSecondaryContainer: Color,   // Color para texto e iconos encima de 'secondaryContainer'.

    // Roles de fondo y superficie (la base de tu app)
    val background: Color,             // Color del fondo general de la pantalla.
    val onBackground: Color,           // Color del texto y contenido principal que va sobre 'background'.
    val surface: Color,                // Color de superficies de componentes como Cards, Menús, Dialogs.
    val onSurface: Color,              // Color del texto y contenido que va sobre 'surface'.
    val surfaceVariant: Color,         // Una variante de 'surface' para diferenciar elementos (ej. contorno de un TextField).
    val onSurfaceVariant: Color        // Color del texto y contenido sobre 'surfaceVariant'.
)

val localMyAppColorScheme = staticCompositionLocalOf {
    // Valores por defecto para evitar crashes si no se provee un esquema.
    MyColorScheme(
        primary = Color.Unspecified, onPrimary = Color.Unspecified,
        primaryContainer = Color.Unspecified, onPrimaryContainer = Color.Unspecified,
        secondary = Color.Unspecified, onSecondary = Color.Unspecified,
        secondaryContainer = Color.Unspecified, onSecondaryContainer = Color.Unspecified,
        background = Color.Unspecified, onBackground = Color.Unspecified,
        surface = Color.Unspecified, onSurface = Color.Unspecified,
        surfaceVariant = Color.Unspecified, onSurfaceVariant = Color.Unspecified
    )
}

// ------------------- Definición de Esquemas Claro y Oscuro -------------------

val MyLightColorScheme = MyColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = secondaryContainerLight, // Reutilizamos un color suave para variantes
    onSurfaceVariant = onSecondaryContainerLight // Y su contraparte para el texto
)

val MyDarkColorScheme = MyColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = secondaryContainerDark, // Reutilizamos un color oscuro para variantes
    onSurfaceVariant = onSecondaryContainerDark // Y su contraparte para el texto
)

@Composable
fun colorScheme(isDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isDarkTheme) MyDarkColorScheme else MyLightColorScheme
