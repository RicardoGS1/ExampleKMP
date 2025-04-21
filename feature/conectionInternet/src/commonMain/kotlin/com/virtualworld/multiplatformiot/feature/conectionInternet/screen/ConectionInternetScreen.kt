package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack

@Composable
fun ConectionInternetScreen (viewModel: ConectionInternetViewModel, popBackStack:() -> Unit){

    Box(modifier = Modifier.fillMaxSize().padding(MyAppTheme.padding.tiny)){

        ButtonBack(
            onClick = popBackStack
        )

    }



}