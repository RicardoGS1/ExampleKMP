package com.virtualworld.multiplatformiot.domain.conectionInternet.model

import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import kotlinx.serialization.Serializable


@Serializable
data class StateObjectDomain(

    var nombre: String? = null,
    var estado: Boolean? = null
)

@Serializable
data class ArduinoDomain (

    val name: String? ="",
    val active: Boolean = true,
    val state1: Map<String, StateObjectDomain>? = mapOf(),

)