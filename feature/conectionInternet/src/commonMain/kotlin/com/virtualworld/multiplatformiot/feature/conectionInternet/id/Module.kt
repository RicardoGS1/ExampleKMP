package com.virtualworld.multiplatformiot.feature.conectionInternet.id

import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.AddArduinoUseCase
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.GetArduinoUseCase
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.UseCaseInternet
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.ConectionInternetViewModel
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.DetailArduinoViewModel
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.AddArduinoViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val featureConectionInternetModule = module {


    factoryOf(::UseCaseInternet)
    factoryOf(::GetArduinoUseCase)
    factoryOf(::AddArduinoUseCase)

    viewModelOf(::ConectionInternetViewModel)

    viewModelOf(::DetailArduinoViewModel)

    viewModelOf(::AddArduinoViewModel)


}