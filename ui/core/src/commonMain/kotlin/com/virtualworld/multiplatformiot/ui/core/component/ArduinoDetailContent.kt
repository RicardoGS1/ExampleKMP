package com.virtualworld.multiplatformiot.ui.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState

@Composable
fun ArduinoDetailContent(
    arduinoDetail: ArduinosState<ArduinoDomainModel>,
    updateState: (String) -> Unit,
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 140.dp)
    ) {


        when (arduinoDetail) {
            is ArduinosState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        arduinoDetail.exception.message.toString(),
                        fontSize = 16.sp,
                        color = MyAppTheme.colorScheme.onBackground,
                    )
                }
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
                val arduino = arduinoDetail.arduinos
                StatesArduino(
                    arduino = arduino,
                    onEstado1Change = updateState,
                )
            }
        }


//        Box(
//            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
//                .background(MyAppTheme.colorScheme.primary).height(64.dp)
//        ) {
//
//
//            TextButton(onClick = {}, modifier = Modifier.align(Alignment.TopCenter)) {
//                Text("Add estado")
//            }
//        }
    }
}

@Composable
fun StatesArduino(
    arduino: ArduinoDomainModel,
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

            arduino.states?.forEach { state ->
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