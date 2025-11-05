package com.virtualworld.multiplatformiot.data.connectionBLE

import com.virtualworld.connectionBLE.BLERepository
import com.virtualworld.multiplatformiot.dataCoreModule
import org.koin.dsl.module

val dataConnectionBLEModule = module {

    includes(dataCoreModule)

    // Repository
    single<BLERepository> { ImplBLERepository () }

}