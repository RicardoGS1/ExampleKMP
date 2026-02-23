package com.virtualworld.multiplatformiot.data.login

import com.virtualworld.multiplatformiot.domain.login.repository.AuthRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataLoginModule = module {
    single { Firebase.auth }
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
}
