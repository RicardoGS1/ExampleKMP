package com.virtualworld.multiplatformiot.domain.login.model

/**
 * Resultado de una operación de autenticación.
 */
sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val message: String) : AuthResult<Nothing>()
    data object Loading : AuthResult<Nothing>()
}
