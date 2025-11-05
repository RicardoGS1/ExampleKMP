package com.wirtualworld.multiplatformiot.feature.connectionBLE.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.virtualworld.connectionBLE.BLEDeviceDomain
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack
import com.virtualworld.multiplatformiot.ui.core.component.ListViewArduino
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState

@Composable
fun ConnectionBLEScreen(
    popBackStack: () -> Unit,
    goToDetailArduino: (String, String) -> Unit,
    viewModel: ConnectionBLEViewModel,
    valueScroll: (Dp) -> Unit,
) {


    val arduinos by viewModel.arduinosState.collectAsState()

    val selectArduinoName = { name: String ->

        val address =
            (arduinos as ArduinosState.Success<List<BLEDeviceDomain>>).arduinos.find { it.name == name }?.address.toString()
        goToDetailArduino(name, address)

    }

    val listState = rememberLazyListState()

    val scrollValue by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset < 160) listState.firstVisibleItemScrollOffset / 2
            else {
                80
            }
        }
    }


    val canvasSize = remember {
        derivedStateOf {
            (200 - scrollValue).dp
        }
    }

    valueScroll(canvasSize.value)


    Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
        TopBarMenu(popBackStack)
    }


    Box(
        modifier = Modifier.fillMaxSize().padding(top = 120.dp)
    ) {

        when (arduinos) {
            is ArduinosState.Error -> {
                Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
                    Text((arduinos as ArduinosState.Error).exception.message.toString())
                    IconButton(onClick = { viewModel.loadPairedDevices() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refrescar")
                    }
                }

            }

            is ArduinosState.Loading -> {
                CircularProgressIndicator()
            }

            is ArduinosState.Success -> {

                val pairNameState =
                    (arduinos as ArduinosState.Success<List<BLEDeviceDomain>>).arduinos.map {
                        it.name.toString() to it.isConnected
                    }

                ListViewArduino(
                    arduinos = pairNameState,
                    goToDetailArduino = selectArduinoName,
                    goToAddArduino = {},
                    listState = listState,
                )
            }
        }
    }
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


