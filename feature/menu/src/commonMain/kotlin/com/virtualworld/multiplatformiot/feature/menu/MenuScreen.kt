package com.virtualworld.multiplatformiot.feature.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.virtualworld.multiplatformiot.ui.core.localPaddingCommon


@Composable
internal fun MenuScreen(
    goToLocalConection: () -> Unit,
    goToInternetConection: () -> Unit,
    goToBluetoothConection: () -> Unit,
    menuViewModel: MenuViewModel
) {


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            val buttonModifier = Modifier.padding( localPaddingCommon.current.normal)

            Button(
                onClick = goToLocalConection,
                buttonModifier
            ) {
                Text("LocalConnections")
            }

            Button(
                onClick = goToInternetConection,
                buttonModifier
            ) {
                Text("InternetConnections")
            }

            Button(
                onClick = goToBluetoothConection,
                buttonModifier
            ) {
                Text("BluetoothConnections")
            }


        }
    }

}