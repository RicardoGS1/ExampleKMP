package com.wirtualworld.multiplatformiot.feature.connectionBluetooth.id

import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ConnectToArduinoUseCase
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.GetArduinoUseCase
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.GetPairedDevicesUseCase
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.SetChangerStateUseCase
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen.ConnectionBluetoothViewModel
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen.DetailArduinoViewModelB
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureConnectionBluetoothModule = module {


    // Use Cases
    factoryOf(::GetPairedDevicesUseCase)
    factoryOf(::GetArduinoUseCase)
    factoryOf(::ConnectToArduinoUseCase)
    factoryOf(::SetChangerStateUseCase)

    // ViewModels
    viewModelOf(::ConnectionBluetoothViewModel)
    viewModelOf(::DetailArduinoViewModelB)
}