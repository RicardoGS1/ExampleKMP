package com.virtualworld.multiplatformiot.domain.connectionBluetooth

class ConnectToArduinoUseCase(private val repository: BluetoothRepository) {

    suspend fun connectToDevice(addressArduino: String): ResponseState<Boolean>{

        return repository.connectToDevice(addressArduino)

    }




}