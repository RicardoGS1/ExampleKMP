package com.virtualworld.multiplatformiot.data.connectionBLE

import com.virtualworld.multiplatformiot.dataCoreModule
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothRepository
import org.koin.dsl.module

val dataConnectionBluetoothModule = module {

    includes(dataCoreModule)

    // Repository
    single<BluetoothRepository> { ImplBluetoothRepository () }

}