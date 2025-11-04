package com.wirtualworld.multiplatformiot.feature.connectionBLE.id

import com.virtualworld.connectionBLE.ConnectToArduinoUseCase
import com.virtualworld.connectionBLE.GetArduinoUseCase
import com.virtualworld.connectionBLE.GetPairedDevicesUseCase
import com.virtualworld.connectionBLE.SetChangerStateUseCase
import com.wirtualworld.multiplatformiot.feature.connectionBLE.screen.ConnectionBLEViewModel
import com.wirtualworld.multiplatformiot.feature.connectionBLE.screen.DetailArduinoBLEViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureConnectionBLEModule = module {


    // Use Cases
    factoryOf(::GetPairedDevicesUseCase)
    factoryOf(::GetArduinoUseCase)
    factoryOf(::ConnectToArduinoUseCase)
    factoryOf(::SetChangerStateUseCase)

    // ViewModels
    viewModelOf(::ConnectionBLEViewModel)
    viewModelOf(::DetailArduinoBLEViewModel)
}