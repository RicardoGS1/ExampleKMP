package com.virtualworld.multiplatformiot.data.conectionLocal

import com.virtualworld.multiplatformiot.data.core.dataCoreModule
import org.koin.dsl.module

val dataConectionLocalModule = module {

    includes(dataCoreModule)

}