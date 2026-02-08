package com.virtualworld.multiplatformiot.domain.login.usecase

import com.virtualworld.multiplatformiot.domain.login.model.AuthResult
import com.virtualworld.multiplatformiot.domain.login.model.UserDomain
import com.virtualworld.multiplatformiot.domain.login.repository.AuthRepository

class SignInWithEmailUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): AuthResult<UserDomain> {
        if (email.isBlank()) return AuthResult.Error("El correo no puede estar vacío")
        if (password.isBlank()) return AuthResult.Error("La contraseña no puede estar vacía")
        return repository.signInWithEmail(email, password)
    }
}
