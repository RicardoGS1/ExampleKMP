package com.virtualworld.multiplatformiot.domain.conectionInternet.mapper

import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ArduinoDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.StateObjectDomain

fun ArduinoData.mapperToDomain(): ArduinoDomain {

    val stateMap = this.objetos?.associate { stateObject ->

        stateObject.keyObjeto!! to StateObjectDomain(nombre =  stateObject.nombre , estado =  stateObject.estado)

    }

    return ArduinoDomain(
        name = this.nameArduino,
        state1 = stateMap
    )
}

fun StateObject.mapperToStateObjectDomain():StateObjectDomain{

  return  StateObjectDomain(this.nombre,this.estado)

}