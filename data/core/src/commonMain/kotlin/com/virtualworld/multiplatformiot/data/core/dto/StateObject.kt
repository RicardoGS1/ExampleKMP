package com.virtualworld.multiplatformiot.data.core.dto

import kotlinx.serialization.Serializable


@Serializable
data class ArduinoData(
    var nameArduino: String = "",
    var address: String = "",
    var active: Boolean = true,
    var objetos: Map<String, StateObject>? = emptyMap()
)

@Serializable
data class StateObject(
    var nombre: String? = null,
    var estado: Boolean? = null
)
