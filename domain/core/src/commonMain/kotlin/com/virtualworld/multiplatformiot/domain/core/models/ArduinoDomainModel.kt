package com.virtualworld.multiplatformiot.domain.core.models


import kotlinx.serialization.Serializable


@Serializable
data class ArduinoDomainModel(
    val name: String = "",
    val address: String = "",
    val active: Boolean? = null,
    val isConnected: Boolean = false,
    val states: Map<String, StateObjectDomain>? = mapOf(),
)


@Serializable
data class StateObjectDomain(

    var nombre: String? = null,
    var estado: Boolean? = null
)