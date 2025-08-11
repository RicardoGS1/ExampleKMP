package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ArduinoDomain
import com.virtualworld.multiplatformiot.feature.conectionInternet.models.ArduinosState
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack

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


//    when(arcoState){
//        is ArcoState.CloseOpenArc -> {}
//        is ArcoState.OpenCloseArc -> { newArcoState = ArcoState.OpenCloseArc( canvasSize.value) }
//        is ArcoState.StaticCloseArc -> {  newArcoState = ArcoState.StaticCloseArc( canvasSize.value)  }
//        is ArcoState.StaticOpenArc -> TODO()
//    }


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
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {

        //TopBarCanva(ArcoState.CloseArc() )

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
                    ColumArduinos(
                        arduinos = arduinos as ArduinosState.Success<List<ArduinoDomain>>,
                        goToDetailArduino = goToDetailArduino,
                        goToAddArduino = goToAddArduino,
                        listState = listState
                    )
                }
            }
        }
    }


}
//}


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


@Composable
fun ColumArduinos(
    arduinos: ArduinosState.Success<List<ArduinoDomain>>,
    goToDetailArduino: (String) -> Unit,
    goToAddArduino: () -> Unit,
    listState: LazyListState
) {


    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    )
    {

        item {
            Spacer(modifier = Modifier.height(100.dp).fillMaxWidth())
        }

        items(arduinos.arduinos) { arduino ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { goToDetailArduino(arduino.name!!) },
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = arduino.name ?: "Sin nombre",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(
                                if (arduino.active == true) Color(0xFF4CAF50) else Color(
                                    0xFFF44336
                                ),
                                shape = CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (arduino.active == true) "Activo" else "Inactivo",
                        color = if (arduino.active == true) Color(0xFF4CAF50) else Color(
                            0xFFF44336
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(64.dp))

        }
    }
}
