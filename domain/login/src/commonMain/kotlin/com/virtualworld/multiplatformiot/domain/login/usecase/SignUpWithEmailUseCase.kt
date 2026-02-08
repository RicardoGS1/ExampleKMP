package com.virtualworld.multiplatformiot.domain.login.usecase

import com.virtualworld.multiplatformiot.domain.login.model.AuthResult
import com.virtualworld.multiplatformiot.domain.login.model.UserDomain
import com.virtualworld.multiplatformiot.domain.login.repository.AuthRepository

class SignUpWithEmailUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): AuthResult<UserDomain> {
        if (email.isBlank()) return AuthResult.Error("El correo no puede estar vacío")
        if (password.length < 6) return AuthResult.Error("La contraseña debe tener al menos 6 caracteres")
        return repository.signUpWithEmail(email, password)
    }
}
