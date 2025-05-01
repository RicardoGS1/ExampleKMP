package com.virtualworld.multiplatformiot.feature.conectionInternet.id

import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.UseCaseInternet
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.ConectionInternetViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val featureConectionInternetModule = module {


    factoryOf(::UseCaseInternet)

    viewModelOf(::ConectionInternetViewModel)


}