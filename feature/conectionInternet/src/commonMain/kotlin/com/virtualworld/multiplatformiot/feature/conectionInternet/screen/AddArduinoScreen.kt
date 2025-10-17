package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState

@Composable
fun AddArduinoScreen(
    popBackStack: () -> Unit,
    viewModel: AddArduinoViewModel
) {
    val name by viewModel.name.collectAsState()
    val state1 by viewModel.state1.collectAsState()

    val saveState by viewModel.saveState.collectAsState()
    var newKey by remember { mutableStateOf("") }
    var newValue by remember { mutableStateOf(false) }

    LaunchedEffect(saveState) {
        if (saveState is ArduinosState.Success) {
            popBackStack()
        }
    }


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        ButtonBack(onClick = popBackStack)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Agregar Arduino", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Nombre del Arduino") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Estados iniciales:")
        state1.forEach { (key, value) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(key)
                Switch(checked = value, onCheckedChange = { viewModel.onState1Change(key, it) })
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = newKey,
                onValueChange = { newKey = it },
                label = { Text("Nombre del estado") },
                modifier = Modifier.weight(1f)
            )
            Switch(checked = newValue, onCheckedChange = { newValue = it })
            Button(onClick = {
                if (newKey.isNotBlank()) {
                    viewModel.onState1Change(newKey, newValue)
                    newKey = ""
                    newValue = false
                }
            }) { Text("Agregar estado") }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { viewModel.saveArduino() }, enabled = name.isNotBlank()) {
            Text("Guardar Arduino")
        }
        if (saveState is ArduinosState.Error) {
            Text(text = (saveState as ArduinosState.Error).exception.message ?: "Error", color = MaterialTheme.colorScheme.error)
        }
    }
} 