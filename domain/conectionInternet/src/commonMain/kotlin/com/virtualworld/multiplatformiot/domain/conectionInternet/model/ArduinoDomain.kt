package com.virtualworld.multiplatformiot.domain.conectionInternet.model

import kotlinx.serialization.Serializable


@Serializable
data class ArduinoDomain (

    val name: String ="",
    val state1: Map<String, Boolean> = mapOf(),
    val state2: Map<String, Boolean> = mapOf(),
    val state3: Map<String, Boolean> = mapOf(),

)