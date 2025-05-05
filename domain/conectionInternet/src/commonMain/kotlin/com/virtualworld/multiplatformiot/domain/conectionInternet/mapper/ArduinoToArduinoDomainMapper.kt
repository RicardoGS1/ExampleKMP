package com.virtualworld.multiplatformiot.domain.conectionInternet.mapper

import com.virtualworld.multiplatformiot.data.core.dto.Arduino
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ArduinoDomain

fun Arduino.mapperToDomain(): ArduinoDomain {

    return (ArduinoDomain(
        name = this.name,
        state1 = this.state1,
        state2 = this.state2,
        state3 = this.state3
    ))


}