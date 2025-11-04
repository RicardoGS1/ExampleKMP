package com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen.bluetoothClassic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.virtualworld.multiplatformiot.ui.core.component.ArduinoDetailContent
import com.virtualworld.multiplatformiot.ui.core.component.TopBarMenuDetail

@Composable
fun DetailArduinoScreenu(
    arduinoName: String,
    arduinoAddress: String,
    popBackStack: () -> Unit,
    viewModel: DetailArduinoViewModelB
) {


    val arduinoDetail by viewModel.arduinosState.collectAsStateWithLifecycle()
    val updateState = { numberState: String -> viewModel.updateState(numberState,arduinoAddress) }


    LaunchedEffect(arduinoAddress) {
        viewModel.getArduino(arduinoAddress)
    }

    Box(modifier = Modifier.fillMaxSize()) {


        Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
            TopBarMenuDetail(popBackStack, arduinoName)
        }

        ArduinoDetailContent(arduinoDetail, updateState)


    }
}







