package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.StateObject
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ArduinoDomain
import com.virtualworld.multiplatformiot.feature.conectionInternet.models.ArduinosState
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack

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

    Box(modifier = Modifier.fillMaxSize().padding(MyAppTheme.padding.tiny)) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ButtonBack(
                onClick = popBackStack
            )

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
                        onEstado1Change = {key, newValue -> viewModel.updateState(key, newValue) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ArduinoDetailContent(
    arduino: ArduinoDomain,
    onEstado1Change: (String, Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Nombre del Arduino
        Text(
            text = "Arduino: ${arduino.name}",
            style = MaterialTheme.typography.headlineMedium
        )

        // Estados
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Estados:",
                style = MaterialTheme.typography.titleMedium
            )

            arduino.state1?.forEach { state->
                EstadoRow(
                    label = state.value.nombre.toString(),
                    checked = state.value.estado!!,
                    onCheckedChange = { onEstado1Change(state.key, state.value.estado !!)  }
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
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}