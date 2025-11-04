package com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen.bluetoothLE

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ConnectionBluetoothScreenLE(
    popBackStack: () -> Boolean,
    goToDetailArduino: (String, String) -> Unit,
    valueScroll: (Dp) -> Unit,
    viewModel: ConnectionBluetoothLEViewModel
) {

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center  ) {
        Text("Actualmente no esta implementado este modulo para este dispositivo")
    }

}
