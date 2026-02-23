package com.virtualworld.multiplatformiot.domain.login.usecase

import com.virtualworld.multiplatformiot.domain.login.model.AuthResult
import com.virtualworld.multiplatformiot.domain.login.repository.AuthRepository

class SignOutUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(): AuthResult<Unit> = repository.signOut()
}
