package com.virtualworld.multiplatformiot.ui.core.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme

@Composable
fun ButtonMenu (onClick: () -> Unit, modifier: Modifier,text : String) {

    Button(
        onClick = onClick,
        modifier = modifier.padding(MyAppTheme.padding.normal),
        //enabled = TODO(),
        shape = MyAppTheme.shape.button,
        colors = ButtonColors(
            containerColor = MyAppTheme.colorScheme.primary,
            contentColor = MyAppTheme.colorScheme.onPrimary,
            disabledContainerColor = MyAppTheme.colorScheme.primary,
            disabledContentColor = MyAppTheme.colorScheme.onPrimary
        ) ,
//        elevation = TODO(),
 //        border = TODO(),
//        contentPadding = TODO(),
//        interactionSource = TODO(),
    ){
        Text(text = text, style = MyAppTheme.typography.bodyMedium)
    }

}