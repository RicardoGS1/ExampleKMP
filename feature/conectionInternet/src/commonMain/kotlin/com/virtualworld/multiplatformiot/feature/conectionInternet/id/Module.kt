package com.virtualworld.multiplatformiot.feature.conectionInternet.id

import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.AddArduinoUseCase
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.GetArduinoDetailUseCase
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.GetListArduinosUseCase
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.SetStateArduinoUseCase
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.ConectionInternetViewModel
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.DetailArduinoViewModel
import com.virtualworld.multiplatformiot.feature.conectionInternet.screen.AddArduinoViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val featureConectionInternetModule = module {


    factoryOf(::GetListArduinosUseCase)
    factoryOf(::GetArduinoDetailUseCase)
    factoryOf(::AddArduinoUseCase)
    factoryOf(::SetStateArduinoUseCase)

    viewModelOf(::ConectionInternetViewModel)

    viewModelOf(::DetailArduinoViewModel)

    viewModelOf(::AddArduinoViewModel)


}