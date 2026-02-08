package com.virtualworld.multiplatformiot.domain.login.usecase

import com.virtualworld.multiplatformiot.domain.login.model.UserDomain
import com.virtualworld.multiplatformiot.domain.login.repository.AuthRepository

class GetCurrentUserUseCase(private val repository: AuthRepository) {

    operator fun invoke(): UserDomain? = repository.currentUser
}
