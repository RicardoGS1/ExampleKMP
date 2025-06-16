package com.virtualworld.multiplatformiot.data.core.dto

import kotlinx.serialization.Serializable


@Serializable
data class StateObject(
    var keyObjeto: String? = null,
    var nombre: String? = null,
    var estado: Boolean? = null
)


@Serializable
data class ArduinoData(

    var nameArduino: String? = null,
    //var objetos:List<StateObject>? = emptyList()
    var objetos: List< StateObject>? = emptyList()
)

