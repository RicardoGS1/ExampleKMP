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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.TopBarCanva


@Composable
internal fun MenuScreen(
    goToLocalConection: () -> Unit,
    goToInternetConection: () -> Unit,
    goToBluetoothConection: () -> Unit,
    menuViewModel: MenuViewModel
) {

    val arduinoActiveInternet by menuViewModel.arduinoActivesInternet.collectAsState()

    LaunchedEffect(Unit){
        menuViewModel.getStatesArduinosInternet()
    }

        Box(modifier = Modifier.fillMaxSize().padding(vertical = 32.dp)) {

            TopBarMenu()

            when (arduinoActiveInternet) {
                is StateScreenMenu.Error -> {}
                is StateScreenMenu.Loading -> {}
                is StateScreenMenu.Success<*> -> {

                    val activate = arduinoActiveInternet as StateScreenMenu.Success

                    AllConnections(
                        goToInternetConection,
                        goToLocalConection,
                        goToBluetoothConection,
                        activate.arduinos

                    )
                }
            }
        }

    }



@Composable
fun TopBarMenu() {

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
                        modifier = Modifier.size(10.dp).background(Color.White, CircleShape)
                            .padding(end = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
            Box(
                modifier = Modifier
                    .size(48.dp) // Tamaño del círculo
                    .clip(CircleShape)
                    .background(Color.White) // Color de fondo del círculo

            ) {
                Icon(
                    imageVector = MyAppTheme.myIcons.person,
                    contentDescription = "",
                    tint = Color.Black, // O el color que prefieras para el icono
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(1.dp) // Espaciado interno del icono dentro del círculo
                )
            }
        }


        Text(
            text = "Conexciones",
            color = Color.White,
            style = MyAppTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 24.dp)
        )

    }


}


@Composable
internal fun AllConnections(
    goToInternetConection: () -> Unit,
    goToLocalConection: () -> Unit,
    goToBluetoothConection: () -> Unit,
    activate: Map<String, Int>,


    ) {


    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 140.dp)
            .padding(horizontal = MyAppTheme.padding.large)

    ) {


        // Tarjeta Internet
        CardConnections(
            title = "Internet",
            icon = MyAppTheme.myIcons.internet,
            color = MyAppTheme.colorScheme.primary,
            arduinosOn = activate["activate"]!!,
            arduinosOff = activate["deactivate"]!!,
            onInfo = {},
            goTo = goToInternetConection,

            )

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta Bluetooth
        CardConnections(
            title = "Bluetooth",
            icon = MyAppTheme.myIcons.bluetooth, // Cambia por un icono de Bluetooth si lo tienes
            color = MyAppTheme.colorScheme.primary,
            arduinosOn = 1,
            arduinosOff = 1,
            onInfo = {},
            goTo = goToBluetoothConection,

            )
    }
}


@Composable
fun CardConnections(
    title: String,
    icon: ImageVector,
    color: Color,
    arduinosOn: Int,
    arduinosOff: Int,
    onInfo: () -> Unit,
    goTo: () -> Unit,

    ) {
    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {


        Column(
            modifier = Modifier.padding(MyAppTheme.padding.large)
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {


                Column() {

                    Text("Tipo", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(title, style = MyAppTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        "Arduinos", color = Color.Gray, style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Row() {
                        Icon(
                            Icons.Default.AddCircle,
                            contentDescription = "On",
                            tint = Color.Green,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            "$arduinosOn",
                            color = Color.Gray,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Icon(
                            Icons.Default.AddCircle,
                            contentDescription = "Off",
                            tint = Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                        Text("$arduinosOff", color = Color.Gray)
                    }
                }

                Box(
                    modifier = Modifier
                        .size(64.dp) // Tamaño del círculo
                        .clip(CircleShape)
                        .background(color) // Color de fondo del círculo

                ) {
                    Icon(
                        icon,
                        contentDescription = title,
                        tint = Color.White, // O el color que prefieras para el icono
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp) // Espaciado interno del icono dentro del círculo
                    )
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                Icon(
                    MyAppTheme.myIcons.info,
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )

                IconButton(
                    onClick = goTo,
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "")
                }

            }


        }


    }
}
