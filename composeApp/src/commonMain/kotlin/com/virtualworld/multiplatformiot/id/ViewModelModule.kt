package com.virtualworld.multiplatformiot.id


import com.virtualworld.multiplatformiot.presentation.ui.HomeViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val viewModelsModule = module {
    viewModelOf(::HomeViewModel)
   
}



