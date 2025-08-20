package com.virtualworld.multiplatformiot.data.core.dto

import kotlinx.serialization.Serializable


@Serializable
data class StateObject(
    var nombre: String? = null,
    var estado: Boolean? = null
)


@Serializable
data class ArduinoData(

    var nameArduino: String? = null,
    var active: Boolean = true,
    var objetos: Map<String, StateObject>? = emptyMap()
)

