package com.virtualworld.multiplatformiot.feature.menu


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.remember
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.Modifier
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize


@Composable
internal fun MenuScreen(
    goToLocalConection: () -> Unit,
    goToInternetConection: () -> Unit,
    goToBluetoothConection: () -> Unit,
    goToBluetoothLEConection: () -> Unit,
    goToProfile: () -> Unit = {},
) {


    Box(
        modifier = Modifier.fillMaxSize()
            .padding(vertical = 32.dp)
    ) {

        ContainerTopBarMenu(onProfileClick = goToProfile)

        AllConnections(
            goToInternetConection,
            goToLocalConection,
            goToBluetoothConection,
            goToBluetoothLEConection
        )
    }
}


@Composable
fun ContainerTopBarMenu(onProfileClick: () -> Unit = {}) {

    val canvasSize = 300.dp


    Box(
        modifier = Modifier.fillMaxWidth().height(canvasSize * 0.6f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                repeat(3) {
                    Box(
                        modifier = Modifier.size(10.dp)
                            .background(Color.White, CircleShape)
                            .padding(end = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
            Box(
                modifier = Modifier.size(48.dp) // Tamaño del círculo
                    .clip(CircleShape)
                    .background(Color.White) // Color de fondo del círculo
                    .clickable(onClick = onProfileClick),
            ) {
                Icon(
                    imageVector = MyAppTheme.myIcons.person,
                    contentDescription = "Ver perfil",
                    tint = Color.DarkGray,
                    modifier = Modifier.fillMaxSize()
                        .padding(4.dp) // Espaciado interno del icono dentro del círculo
                )
            }
        }


        Text(
            text = "Conexiones",
            color = Color.White,
            style = MyAppTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 24.dp)
        )

    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AllConnections(
    goToInternetConection: () -> Unit,
    goToLocalConection: () -> Unit,
    goToBluetoothConection: () -> Unit,
    goToBluetoothLEConection: () -> Unit,
) {

    BoxWithConstraints(modifier = Modifier.fillMaxWidth().padding(top = 140.dp)) {

        val connections = remember {
            listOf(
                "internet" to goToInternetConection,
                "local" to goToLocalConection,
                "bluetooth" to goToBluetoothConection,
                "ble" to goToBluetoothLEConection
            )
        }

        val columns = if (maxWidth > 600.dp) 2 else 1

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier.fillMaxWidth().padding(horizontal = MyAppTheme.padding.large),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                connections,
                key = { it.first }) { (type, goTo) ->
                val cardModifier =
                    Modifier.animateItem(
                        fadeInSpec = null,
                        fadeOutSpec = null,
                        placementSpec = tween(durationMillis = 500) // Animación de 0.5 segundos
                    )

                when (type) {
                    "internet" -> CardConnections(
                        modifier = cardModifier,
                        type = "Internet",
                        detail = "Firebase",
                        icon = MyAppTheme.myIcons.internet,
                        color = MyAppTheme.colorScheme.primary,
                        onInfo = "Debes conectar el modulo Arduino o ESP32 a la base de datos usando apiKey: \"AIzaSyBqVQi5_zsr88KTKG4N9QcQ5GAsslD_Esc\" y projectId: \"multiplatformiot\" para mas detalles visita el repo https://github.com/RicardoGS1/ExampleKMP ",
                        goTo = goTo,
                    )

                    "local" -> CardConnections(
                        modifier = cardModifier,
                        type = "Red Local",
                        detail = "Wifi",
                        icon = MyAppTheme.myIcons.internet,
                        color = MyAppTheme.colorScheme.primary,
                        onInfo = "Para mas informacion sobre esta conexion visita el repo https://github.com/RicardoGS1/ExampleKMP",
                        goTo = goTo,
                    )

                    "bluetooth" -> CardConnections(
                        modifier = cardModifier,
                        type = "Bluetooth",
                        detail = "Classic",
                        icon = MyAppTheme.myIcons.bluetooth,
                        color = MyAppTheme.colorScheme.primary,
                        onInfo = "Para usar la conexión Bluetooth classic debes comenzar el nombre el modulo del arduino o el ESP32 con \"arduino\" para mas detalles de la configuración visita el repo https://github.com/RicardoGS1/ExampleKMP  ",
                        goTo = goTo,
                    )

                    "ble" -> CardConnections(
                        modifier = cardModifier,
                        type = "Bluetooth",
                        detail = "Low Energy",
                        icon = MyAppTheme.myIcons.bluetooth,
                        color = MyAppTheme.colorScheme.primary,
                        onInfo = "Para la conexion Bluetooth Low Energy debes comenzar el nombre del modulo del arduino o el ESP32 con \"arduino\" usar el servicio \"0000181C-1234-1000-8000-00805F9B34FB\" para configurar las caracteristicas visita el repo https://github.com/RicardoGS1/ExampleKMP ",
                        goTo = goTo,
                    )
                }
            }
        }
    }
}


@Composable
fun CardConnections(
    modifier: Modifier = Modifier,
    type: String,
    detail: String,
    icon: ImageVector,
    color: Color,
    onInfo: String,
    goTo: () -> Unit,
) {
    // 1. Estado para controlar si la tarjeta está girada o no.
    // Uso rememberSaveable para que el estado sobreviva a rotaciones de pantalla.
    var isFlipped by rememberSaveable { mutableStateOf(false) }

    // 2. Animación del valor de rotación en el eje Y.
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600), // Duración de la animación
        label = "rotationY"
    )

    //  Estado para almacenar el tamaño del anverso (lado frontal)
    var frontSize by remember { mutableStateOf(IntSize.Zero) }


    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = modifier
            .fillMaxWidth()
            // 3. Aplicamos la rotación aquí. No la propagamos dentro de la tarjeta.
            .graphicsLayer {
                this.rotationY = rotationY
                // Opcional: añade un efecto de cámara para que el giro se vea más 3D
                cameraDistance = 12f * density
            },
        colors = CardDefaults.cardColors(containerColor = MyAppTheme.colorScheme.surface),

        onClick = {
            if (isFlipped) {
                isFlipped = !isFlipped
            } else {
                goTo()
            }
        }
    ) {
        // Lógica para mostrar el anverso o el reverso

        if (rotationY <= 90f) {
            // MOSTRAMOS EL ANVERSO
            Column(
                modifier = Modifier
                    .graphicsLayer { this.rotationY = 0f }
                    // 2. Medimos el tamaño del anverso y lo guardamos
                    .onSizeChanged { frontSize = it }
            ) {
                CardFrontContent(
                    type, detail, icon, color,
                    goTo = goTo, // Pasamos el goTo al botón de flecha
                    onInfoClick = { isFlipped = true } // El icono de info gira la tarjeta
                )
            }
        } else {
            // MOSTRAMOS EL REVERSO
            Column(
                modifier = Modifier
                    .graphicsLayer { this.rotationY = 180f }
                    // 3. Aplicamos el tamaño medido del anverso al reverso
                    .size(
                        width = with(LocalDensity.current) { frontSize.width.toDp() },
                        height = with(LocalDensity.current) { frontSize.height.toDp() }
                    )
            ) {
                CardBackContent(onInfo)
            }
        }
    }
}


@Composable
private fun CardFrontContent(
    type: String,
    detail: String,
    icon: ImageVector,
    color: Color,
    goTo: () -> Unit,
    onInfoClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(MyAppTheme.padding.large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    "Conexión",
                    color = MyAppTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    type,
                    color = MyAppTheme.colorScheme.onSurface,
                    style = MyAppTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    "Tipo",
                    color = MyAppTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    detail,
                    color = MyAppTheme.colorScheme.onSurface,
                    style = MyAppTheme.typography.titleSmall
                )
            }
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(color)
            ) {
                Icon(
                    icon,
                    contentDescription = type,
                    tint = MyAppTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                MyAppTheme.myIcons.info,
                contentDescription = "Info",
                modifier = Modifier
                    .size(32.dp)
                    .clickable(onClick = onInfoClick),
                tint = MyAppTheme.colorScheme.secondary
            )
            IconButton(onClick = goTo) {
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "Go to connection",
                    tint = MyAppTheme.colorScheme.primary
                )
            }
        }
    }
}

// Composable para el DORSO de la tarjeta (el texto de información)
@Composable
private fun CardBackContent(infoText: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(MyAppTheme.padding.large),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = infoText,
            style = MaterialTheme.typography.bodySmall,
            color = MyAppTheme.colorScheme.onSurface
        )
    }
}



