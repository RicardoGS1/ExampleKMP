package com.wirtualworld.multiplatformiot.feature.conectionLocal.screen

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
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack
import com.virtualworld.multiplatformiot.ui.core.component.ListViewArduinoStates

@Composable
internal fun ConectionLocalScreen(
    viewModel: ConectionLocalViewModel,
    popBackStack: () -> Unit,
    valueScroll: (Dp) -> Unit,
) {


    val arduinos by viewModel.arduinosState.collectAsState()
    val goToDetailArduino = { name: String -> }




    Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
        TopBarMenu(popBackStack)
    }

    ListViewArduinoStates(
        stateArduino = arduinos,
        goToDetailArduino = goToDetailArduino,
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