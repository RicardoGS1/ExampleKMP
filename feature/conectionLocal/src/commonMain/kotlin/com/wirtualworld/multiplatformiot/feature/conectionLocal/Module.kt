package com.wirtualworld.multiplatformiot.feature.conectionLocal


import com.virtualworld.multiplatformiot.domain.conectionLocal.domainConectionLocalModule
import com.wirtualworld.multiplatformiot.feature.conectionLocal.screen.ConectionLocalViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureConectionLocalModule = module {

    includes( domainConectionLocalModule )

    viewModelOf(::ConectionLocalViewModel)


}