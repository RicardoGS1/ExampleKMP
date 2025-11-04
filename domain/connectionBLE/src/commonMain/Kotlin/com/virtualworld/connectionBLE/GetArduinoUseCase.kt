package com.virtualworld.connectionBLE

import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomain
import kotlinx.coroutines.flow.Flow

class GetArduinoUseCase(private val bluetoothRepository: BLERepository) {

    fun getArduino(): Flow<ResponseState<ArduinoDomain>> {

       return bluetoothRepository.getAllStatesFlow()

//        val arduinoDomain = ArduinoDomain(
//            name = "ARDUINO NANO",
//            active = true,
//            state1 = mapOf(
//                "state1" to StateObjectDomain("cosina", true),
//                "state2" to StateObjectDomain("cuarto", false)
//            )
//
//        )
//
        //       return ResponseState.Success( arduinoDomain)

    }

}