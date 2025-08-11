package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ArduinoDomain
import com.virtualworld.multiplatformiot.feature.conectionInternet.models.ArduinosState
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack
import com.virtualworld.multiplatformiot.ui.core.component.TopBarCanva

@Composable
fun DetailArduinoScreen(
    arduinoName: String,
    popBackStack: () -> Unit,
    viewModel: DetailArduinoViewModel
) {
    val arduinos by viewModel.arduinosState.collectAsState()

    LaunchedEffect(arduinoName) {
        viewModel.getArduino(arduinoName)
    }

    Box(modifier = Modifier.fillMaxSize()) {


       // TopBarCanva(ArcoState.CloseArc(140.dp) )

        Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
            TopBarMenuDetail(popBackStack, arduinoName)
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 140.dp)
        ) {


            when (arduinos) {
                is ArduinosState.Error -> {
                    Text((arduinos as ArduinosState.Error).exception.message.toString())
                }

                is ArduinosState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ArduinosState.Success -> {
                    val arduino = (arduinos as ArduinosState.Success<ArduinoDomain>).arduinos
                    ArduinoDetailContent(
                        arduino = arduino,
                        onEstado1Change = { key -> viewModel.updateState(key) },
                    )
                }
            }
        }


        Box(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                .background(MyAppTheme.colorScheme.primary).height(64.dp)
        ) {


            TextButton(onClick = {}, modifier = Modifier.align(Alignment.TopCenter)) {
                Text("Add estado")
            }
        }
    }


}

@Composable
fun TopBarMenuDetail(popBackStack: () -> Unit, arduinoName: String) {


    Column {
        ButtonBack(
            onClick = popBackStack
        )

        // Spacer(Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = arduinoName,
                color = Color.White,
                style = MyAppTheme.typography.titleLarge,
            )

            Switch(
                checked = true,
                onCheckedChange = {}
            )
        }

    }
}

@Composable
private fun ArduinoDetailContent(
    arduino: ArduinoDomain,
    onEstado1Change: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Nombre del Arduino


        // Estados
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Estados:",
                style = MaterialTheme.typography.bodyLarge
            )

            arduino.state1?.forEach { state ->
                EstadoRow(
                    label = state.value.nombre.toString(),
                    checked = state.value.estado!!,
                    onCheckedChange = { onEstado1Change(state.key) }
                )
            }

        }
    }
}

@Composable
private fun EstadoRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(label)
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}