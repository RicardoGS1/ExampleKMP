package com.virtualworld.multiplatformiot.domain.login.model

import kotlinx.serialization.Serializable

/**
 * Modelo de dominio del usuario autenticado.
 */
@Serializable
data class UserDomain(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: String?
)
