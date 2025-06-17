package com.virtualworld.multiplatformiot.ui.core.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme

@Composable
fun ButtonMenu(onClick: () -> Unit, modifier: Modifier, text: String) {

    Button(
        onClick = onClick,
        modifier = modifier.padding(MyAppTheme.padding.normal).width(256.dp),
        shape = MyAppTheme.shape.button,
        colors = ButtonColors(
            containerColor = MyAppTheme.colorScheme.primary,
            contentColor = MyAppTheme.colorScheme.onPrimary,
            disabledContainerColor = MyAppTheme.colorScheme.primary,
            disabledContentColor = MyAppTheme.colorScheme.onPrimary
        ),
        contentPadding = PaddingValues(all = 12.dp)
    ) {
        Text(text = text, style = MyAppTheme.typography.bodyMedium, textAlign = TextAlign.Center)
    }

}