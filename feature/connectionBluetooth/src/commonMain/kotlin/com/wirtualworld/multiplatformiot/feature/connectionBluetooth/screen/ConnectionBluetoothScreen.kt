package com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothDeviceDomain
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack
import com.virtualworld.multiplatformiot.ui.core.component.ListViewArduino
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState

@Composable
fun ConnectionBluetoothScreen(
    popBackStack: () -> Unit,
    goToDetailArduino: (String, String) -> Unit,
    goToAddArduino: () -> Unit,
    viewModel: ConnectionBluetoothViewModel,
    valueScroll: (Dp) -> Unit,
) {


    val arduinos by viewModel.arduinosState.collectAsState()

    val selectArduinoName = { name: String ->

        val address =
            (arduinos as ArduinosState.Success<List<BluetoothDeviceDomain>>).arduinos.find { it.name == name }?.address.toString()
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


    Scaffold(
        containerColor = Color.Transparent, floatingActionButton = {
            FloatingActionButton(
                onClick = { goToAddArduino() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Agregar Arduino"
                )
            }
        }, contentWindowInsets = WindowInsets.waterfall
    ) { paddingValues ->


        Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
            TopBarMenu(popBackStack)
        }


        Box(
            modifier = Modifier.fillMaxSize().padding(top = 120.dp)
        ) {

            when (arduinos) {
                is ArduinosState.Error -> {
                    Text((arduinos as ArduinosState.Error).exception.message.toString())
                }

                is ArduinosState.Loading -> {
                    CircularProgressIndicator()
                }

                is ArduinosState.Success -> {

                    val pairNameState =
                        (arduinos as ArduinosState.Success<List<BluetoothDeviceDomain>>).arduinos.map {
                            it.name.toString() to it.isConnected
                        }

                    val completeGoToDetailArduino = {

                    }




                    ListViewArduino(
                        arduinos = pairNameState,
                        goToDetailArduino = selectArduinoName,
                        goToAddArduino = goToAddArduino,
                        listState = listState,
                    )
                }
            }
        }
    }


}

fun getAddress(name: String) {

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





