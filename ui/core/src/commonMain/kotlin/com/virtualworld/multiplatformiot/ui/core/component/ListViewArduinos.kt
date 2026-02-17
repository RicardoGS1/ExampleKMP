package com.virtualworld.multiplatformiot.ui.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState


@Composable
fun ListViewArduinoStates(
    stateArduino: ArduinosState<List<ArduinoDomainModel>>,
    goToDetailArduino: (String) -> Unit,
    valueScroll: (Dp) -> Unit
) {

    val listState = rememberLazyListState()

    val scrollValue by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset < 140) listState.firstVisibleItemScrollOffset / 2
            else {
                RECT_HMEDIUM - RECT_SMALL
            }
        }
    }


    val canvasSize = remember {
        derivedStateOf {
            (RECT_HMEDIUM - scrollValue.toFloat()).dp
        }
    }

    valueScroll(canvasSize.value)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = (RECT_SMALL).dp)
    ) {

        when (stateArduino) {
            is ArduinosState.Error -> {
                Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
                    Text(
                        text = stateArduino.exception.message.toString(),
                        color = MyAppTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(MyAppTheme.padding.normal),
                    )
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refrescar",
                            tint = MyAppTheme.colorScheme.onBackground
                        )

                    }
                }
            }


            is ArduinosState.Loading -> {
                CircularProgressIndicator()
            }

            is ArduinosState.Success -> {

                ListArduinoSuccess(
                    arduinos = stateArduino.arduinos,
                    goToDetailArduino = goToDetailArduino,
                    listState = listState,
                )
            }
        }
    }
}

@Composable
fun ListArduinoSuccess(
    arduinos: List<ArduinoDomainModel>,
    goToDetailArduino: (String) -> Unit,
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

        items(arduinos) { arduino ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { goToDetailArduino(arduino.name) },
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = MyAppTheme.colorScheme.surface),
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = arduino.name,
                            style = MaterialTheme.typography.titleMedium,
                            //modifier = Modifier.weight(1f),
                            color = MyAppTheme.colorScheme.onSurface
                        )
                        Text(
                            text = arduino.address,
                            style = MaterialTheme.typography.titleSmall,
                            //modifier = Modifier.weight(1f),
                            color = MyAppTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    val textActive: Color = when (arduino.active) {
                        true -> {
                            Color(0xFF4CAF50)
                        }

                        false -> {
                            Color(0xFFF44336)
                        }

                        null -> {
                            Color.Gray
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(textActive, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (arduino.active == true) "Activo" else if (arduino.active == false) "Inactivo" else "Desconocido",
                        color = textActive,
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