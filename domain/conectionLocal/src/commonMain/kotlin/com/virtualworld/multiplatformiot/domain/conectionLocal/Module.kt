package com.virtualworld.multiplatformiot.domain.conectionLocal


import com.virtualworld.multiplatformiot.data.conectionLocal.dataConectionLocalModule
import org.koin.dsl.module


val domainConectionLocalModule = module {

    includes(dataConectionLocalModule)

}