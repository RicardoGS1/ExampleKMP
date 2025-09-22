package com.virtualworld.multiplatformiot.domain.connectionBluetooth

import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomain
import com.virtualworld.multiplatformiot.domain.core.models.StateObjectDomain
import kotlinx.coroutines.flow.Flow

class GetArduinoUseCase(private val bluetoothRepository: BluetoothRepository) {

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