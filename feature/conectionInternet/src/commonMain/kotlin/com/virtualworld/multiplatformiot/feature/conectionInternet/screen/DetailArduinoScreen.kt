package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.ui.core.component.ArduinoDetailContent
import com.virtualworld.multiplatformiot.ui.core.component.TopBarMenuDetail

@Composable
fun DetailArduinoScreen(
    arduinoName: String,
    popBackStack: () -> Unit,
    viewModel: DetailArduinoViewModel
) {
    val arduinoDetail by viewModel.arduinosState.collectAsState()

    LaunchedEffect(arduinoName) {
        viewModel.getDetailArduino(arduinoName)
    }

    val updateState = { keyState: String -> viewModel.updateState(keyState) }

    Box(modifier = Modifier.fillMaxSize()) {


        // TopBarCanva(ArcoState.CloseArc(140.dp) )

        Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
            TopBarMenuDetail(popBackStack, arduinoName)
        }

        ArduinoDetailContent(arduinoDetail, updateState)


    }
}