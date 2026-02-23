package com.virtualworld.multiplatformiot.domain.login.repository

import com.virtualworld.multiplatformiot.domain.login.model.AuthResult
import com.virtualworld.multiplatformiot.domain.login.model.UserDomain
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio de autenticación. Abstraction sobre Firebase Auth u otra fuente.
 */
interface AuthRepository {

    /**
     * Estado actual del usuario (null = no autenticado).
     */
    val currentUser: UserDomain?

    /**
     * Flujo del estado de autenticación. Emite el usuario actual o null al cambiar.
     */
    fun authState(): Flow<UserDomain?>

    /**
     * Iniciar sesión con correo y contraseña.
     */
    suspend fun signInWithEmail(email: String, password: String): AuthResult<UserDomain>

    /**
     * Registrar nueva cuenta con correo y contraseña.
     */
    suspend fun signUpWithEmail(email: String, password: String): AuthResult<UserDomain>

    /**
     * Iniciar sesión con credencial de Google (idToken).
     * En Android/iOS se obtiene el token desde el SDK nativo; en Desktop puede no estar disponible.
     */
    suspend fun signInWithGoogle(idToken: String): AuthResult<UserDomain>

    /**
     * Cerrar sesión.
     */
    suspend fun signOut(): AuthResult<Unit>
}
