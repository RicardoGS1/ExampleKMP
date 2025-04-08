package com.virtualworld.multiplatformiot.feature.menu.id

import com.virtualworld.multiplatformiot.feature.menu.MenuViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureMenuModule = module {

    includes()

    viewModelOf(::MenuViewModel)


}