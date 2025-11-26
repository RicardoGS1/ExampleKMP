package com.virtualworld.multiplatformiot.ui.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme


@Composable
fun TopBarMenuDetail(popBackStack: () -> Unit, arduinoName: String) {


    Column {
        ButtonBack(
            onClick = popBackStack
        )

        // Spacer(Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = arduinoName,
                color = Color.White,
                style = MyAppTheme.typography.titleLarge,
            )


        }

    }
}