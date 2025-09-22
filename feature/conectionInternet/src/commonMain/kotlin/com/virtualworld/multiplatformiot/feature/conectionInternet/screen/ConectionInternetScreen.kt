package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomain
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack
import com.virtualworld.multiplatformiot.ui.core.component.ListViewArduino
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState

@Composable
fun ConectionInternetScreen(
    popBackStack: () -> Unit,
    goToDetailArduino: (String) -> Unit,
    goToAddArduino: () -> Unit,
    viewModel: ConectionInternetViewModel,
    valueScroll: (Dp) -> Unit,
) {

    val arduinos by viewModel.arduinosState.collectAsState()

    val listState = rememberLazyListState()

    val scrollValue by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset < 160)
                listState.firstVisibleItemScrollOffset / 2
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
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { goToAddArduino() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Arduino"
                )
            }
        },
        contentWindowInsets = WindowInsets.waterfall
    ) { paddingValues ->


        Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
            TopBarMenu(popBackStack)
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp)
        ) {

            when (arduinos) {
                is ArduinosState.Error -> {
                    Text((arduinos as ArduinosState.Error).exception.message.toString())
                }

                is ArduinosState.Loading -> {
                    CircularProgressIndicator()
                }

                is ArduinosState.Success -> {

                    val pairNameState = (arduinos as ArduinosState.Success<List<ArduinoDomain>>).arduinos.map {
                        it.name.toString() to it.active
                    }

                    ListViewArduino(
                        arduinos = pairNameState,
                        goToDetailArduino = goToDetailArduino,
                        goToAddArduino = goToAddArduino,
                        listState = listState,
                    )
                }
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



