package com.virtualworld.multiplatformiot.data.conectionLocal

import com.virtualworld.multiplatformiot.dataCoreModule
import org.koin.dsl.module

val dataConectionLocalModule = module {

    includes(dataCoreModule)

}