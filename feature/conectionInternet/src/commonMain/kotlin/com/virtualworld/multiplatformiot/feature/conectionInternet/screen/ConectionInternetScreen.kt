package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ArduinoDomain
import com.virtualworld.multiplatformiot.feature.conectionInternet.models.ArduinosState
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack
import com.virtualworld.multiplatformiot.ui.core.component.TopBarCanva

@Composable
fun ConectionInternetScreen(
    popBackStack: () -> Unit,
    goToDetailArduino: (String) -> Unit,
    goToAddArduino: () -> Unit,
    viewModel: ConectionInternetViewModel,
) {

    val arduinos by viewModel.arduinosState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {



        Column {

           Box() {
               TopBarCanva(false)

               ButtonBack(
                   onClick = popBackStack
               )
           }
            when (arduinos) {
                is ArduinosState.Error -> {
                    Text((arduinos as ArduinosState.Error).exception.message.toString())
                }

                is ArduinosState.Loading -> {
                    CircularProgressIndicator()
                }

                is ArduinosState.Success -> {
                    ColumArduinos(
                        arduinos = arduinos as ArduinosState.Success<List<ArduinoDomain>>,
                        goToDetailArduino = goToDetailArduino,
                        goToAddArduino = goToAddArduino
                    )
                }
            }
        }
    }
}

@Composable
fun ColumArduinos(
    arduinos: ArduinosState.Success<List<ArduinoDomain>>,
    goToDetailArduino: (String) -> Unit,
    goToAddArduino: () -> Unit
) {
    Column {
        arduinos.arduinos.forEach { arduino ->
            Button(onClick = { goToDetailArduino(arduino.name!!) }) {
                Text(arduino.name!!)
            }
        }
        Button(onClick = { goToAddArduino() }) {
            Text("Agregar Arduino")
        }
    }
}