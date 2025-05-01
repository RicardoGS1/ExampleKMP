package com.virtualworld.multiplatformiot.data.core

import kotlinx.serialization.Serializable


@Serializable
data class Arduino(
    val name: String ="",
    val state1: Map<String, Boolean> = mapOf(),
    val state2: Map<String, Boolean> = mapOf(),
    val state3: Map<String, Boolean> = mapOf(),

)


