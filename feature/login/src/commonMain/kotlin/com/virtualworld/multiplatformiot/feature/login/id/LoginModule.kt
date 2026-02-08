package com.virtualworld.multiplatformiot.feature.login.id

import com.virtualworld.multiplatformiot.domain.login.repository.AuthRepository
import com.virtualworld.multiplatformiot.domain.login.usecase.GetCurrentUserUseCase
import com.virtualworld.multiplatformiot.domain.login.usecase.ObserveAuthStateUseCase
import com.virtualworld.multiplatformiot.domain.login.usecase.SignInWithEmailUseCase
import com.virtualworld.multiplatformiot.domain.login.usecase.SignInWithGoogleUseCase
import com.virtualworld.multiplatformiot.domain.login.usecase.SignOutUseCase
import com.virtualworld.multiplatformiot.domain.login.usecase.SignUpWithEmailUseCase
import com.virtualworld.multiplatformiot.feature.login.AuthStateViewModel
import com.virtualworld.multiplatformiot.feature.login.LoginViewModel
import com.virtualworld.multiplatformiot.feature.login.RegisterViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureLoginModule = module {
    factoryOf(::SignInWithEmailUseCase)
    factoryOf(::SignUpWithEmailUseCase)
    factoryOf(::SignInWithGoogleUseCase)
    factoryOf(::GetCurrentUserUseCase)
    factoryOf(::ObserveAuthStateUseCase)
    factoryOf(::SignOutUseCase)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::AuthStateViewModel)
}
