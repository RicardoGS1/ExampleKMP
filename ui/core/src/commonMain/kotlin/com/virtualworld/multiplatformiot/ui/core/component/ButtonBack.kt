package com.virtualworld.multiplatformiot.ui.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme

@Composable
fun ButtonBack(onClick: () -> Unit){

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .clip(CircleShape)
            .background(color) // Color de fondo
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Atrás",
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp),// Icono de flecha hacia atrás
            tint = MyAppTheme.colorScheme.onPrimary
        )
    }



}