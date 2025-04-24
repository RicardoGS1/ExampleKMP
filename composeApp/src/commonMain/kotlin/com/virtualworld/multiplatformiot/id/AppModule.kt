package com.virtualworld.multiplatformiot.id

import com.virtualworld.multiplatformiot.feature.conectionInternet.id.featureConectionInternetModule
import com.virtualworld.multiplatformiot.feature.menu.id.featureMenuModule
import com.wirtualworld.multiplatformiot.feature.conectionLocal.featureConectionLocalModule
import org.koin.dsl.module

val appModule = module {
    includes(featureMenuModule, featureConectionLocalModule,featureConectionInternetModule)

}