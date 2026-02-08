package com.virtualworld.multiplatformiot.domain.login.usecase

import com.virtualworld.multiplatformiot.domain.login.model.AuthResult
import com.virtualworld.multiplatformiot.domain.login.model.UserDomain
import com.virtualworld.multiplatformiot.domain.login.repository.AuthRepository

class SignInWithGoogleUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(idToken: String): AuthResult<UserDomain> {
        if (idToken.isBlank()) return AuthResult.Error("Token de Google no válido")
        return repository.signInWithGoogle(idToken)
    }
}
