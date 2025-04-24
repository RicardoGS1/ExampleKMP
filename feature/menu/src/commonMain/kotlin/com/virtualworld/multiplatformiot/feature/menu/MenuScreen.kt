package com.virtualworld.multiplatformiot.feature.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.virtualworld.multiplatformiot.ui.core.component.ButtonMenu



@Composable
internal fun MenuScreen(
    goToLocalConection: () -> Unit,
    goToInternetConection: () -> Unit,
    goToBluetoothConection: () -> Unit,
    menuViewModel: MenuViewModel
) {


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            ButtonMenu(
                onClick = goToLocalConection,
                modifier = Modifier,
                text = "LocalConnections"
            )

            ButtonMenu(
                onClick = goToInternetConection,
                modifier = Modifier,
                text = "InternetConnections"
            )

            ButtonMenu(
                onClick = goToBluetoothConection,
                modifier = Modifier,
                text = "BluetoothConnections"
            )


        }
    }

}