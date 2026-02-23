package com.virtualworld.multiplatformiot.feature.profile.id

import com.virtualworld.multiplatformiot.domain.login.usecase.GetCurrentUserUseCase
import com.virtualworld.multiplatformiot.domain.login.usecase.SignOutUseCase
import com.virtualworld.multiplatformiot.feature.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureProfileModule = module {
    viewModelOf(::ProfileViewModel)
}
