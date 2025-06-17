package com.wirtualworld.multiplatformiot.feature.conectionLocal.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack

@Composable
internal fun ConectionLocalScreen (viewModel: ConectionLocalViewModel, popBackStack: () -> Unit) {

    Box(modifier = Modifier.fillMaxSize().padding(MyAppTheme.padding.tiny)){

        ButtonBack(
            onClick = popBackStack
        )

    }


}