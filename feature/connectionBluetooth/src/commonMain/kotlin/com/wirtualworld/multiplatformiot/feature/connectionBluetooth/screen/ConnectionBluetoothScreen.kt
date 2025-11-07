package com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack
import com.virtualworld.multiplatformiot.ui.core.component.ListViewArduinoStates
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState

@Composable
fun ConnectionBluetoothScreen(
    popBackStack: () -> Unit,
    goToDetailArduino: (String, String) -> Unit,
    viewModel: ConnectionBluetoothViewModel,
    valueScroll: (Dp) -> Unit,
) {


    val arduinos by viewModel.arduinosState.collectAsState()

    val selectArduinoName = { name: String ->

        val address =
            (arduinos as ArduinosState.Success<List<ArduinoDomainModel>>).arduinos.find { it.name == name }?.address.toString()
        goToDetailArduino(name, address)

    }

    Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
        TopBarMenu(popBackStack)
    }

    ListViewArduinoStates(
        stateArduino = arduinos,
        goToDetailArduino = selectArduinoName,
        valueScroll = valueScroll,
    )
}



@Composable
fun TopBarMenu(popBackStack: () -> Unit) {


    Column {
        ButtonBack(
            onClick = popBackStack
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Lista de Arduinos",
            color = Color.White,
            style = MyAppTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 24.dp)
        )

    }
}





