package com.virtualworld.multiplatformiot.feature.conectionInternet.id

import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.ConectionInternetViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val featureConectionInternetModule = module {

    //includes( domainConectionLocalModule )

    viewModelOf(::ConectionInternetViewModel)


}