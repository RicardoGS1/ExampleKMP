package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState

@Composable
fun AddArduinoScreen(
    popBackStack: () -> Unit,
    viewModel: AddArduinoViewModel
) {
    val name by viewModel.name.collectAsState()
    val stateObjects by viewModel.stateObjects.collectAsState()
    val saveState by viewModel.saveState.collectAsState()

    var newStateName by remember { mutableStateOf("") }
    var newStateValue by remember { mutableStateOf(false) }

    LaunchedEffect(saveState) {
        if (saveState is ArduinosState.Success<*>) {
            popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ButtonBack(onClick = popBackStack)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Agregar Arduino",
            style = MaterialTheme.typography.headlineMedium,
            color = MyAppTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Nombre del Arduino") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Estados (máximo $MAX_STATE_OBJECTS)",
                style = MaterialTheme.typography.titleMedium,
                color = MyAppTheme.colorScheme.onBackground
            )
            Text(
                text = "Cada estado tiene un nombre y un valor on/off.",
                style = MaterialTheme.typography.bodySmall,
                color = MyAppTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(12.dp))

            stateObjects.forEach { (key, value) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MyAppTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = key,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MyAppTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (value) "On" else "Off",
                                style = MaterialTheme.typography.bodySmall,
                                color = MyAppTheme.colorScheme.onSurface
                            )
                            Switch(
                                checked = value,
                                onCheckedChange = { viewModel.updateStateObjectValue(key, it) }
                            )
                            IconButton(
                                onClick = { viewModel.removeStateObject(key) }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Eliminar estado",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }

            if (viewModel.canAddMoreStateObjects()) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = MyAppTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Nuevo estado (${stateObjects.size}/$MAX_STATE_OBJECTS)",
                            style = MaterialTheme.typography.labelMedium,
                            color = MyAppTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = newStateName,
                                onValueChange = { newStateName = it },
                                label = { Text("Nombre del estado") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (newStateValue) "On" else "Off",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Switch(
                                    checked = newStateValue,
                                    onCheckedChange = { newStateValue = it }
                                )
                            }
                            IconButton(
                                onClick = {
                                    if (newStateName.isNotBlank()) {
                                        viewModel.addStateObject(newStateName.trim(), newStateValue)
                                        newStateName = ""
                                        newStateValue = false
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Agregar estado"
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            androidx.compose.material3.Button(
                onClick = { viewModel.saveArduino() },
                enabled = name.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Arduino en Firestore")
            }
        }
        if (saveState is ArduinosState.Error) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = (saveState as ArduinosState.Error).exception.message ?: "Error al guardar",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
