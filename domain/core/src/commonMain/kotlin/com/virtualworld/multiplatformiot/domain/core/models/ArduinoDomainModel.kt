package com.virtualworld.multiplatformiot.domain.core.models


import kotlinx.serialization.Serializable


@Serializable
data class ArduinoDomain(
    val name: String? = "",
    val active: Boolean = true,
    val state1: Map<String, StateObjectDomain>? = mapOf(),
)


@Serializable
data class StateObjectDomain(

    var nombre: String? = null,
    var estado: Boolean? = null
)