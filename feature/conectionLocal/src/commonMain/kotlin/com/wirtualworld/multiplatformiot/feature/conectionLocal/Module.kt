package com.wirtualworld.multiplatformiot.feature.conectionLocal


import com.wirtualworld.multiplatformiot.feature.conectionLocal.screen.ConectionLocalViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureConectionLocalModule = module {



    viewModelOf(::ConectionLocalViewModel)


}