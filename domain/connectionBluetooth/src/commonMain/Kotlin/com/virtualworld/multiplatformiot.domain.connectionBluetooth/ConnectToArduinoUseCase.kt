package com.virtualworld.multiplatformiot.domain.connectionBluetooth

class ConnectToArduinoUseCase(private val repository: BluetoothRepository) {

    suspend fun connectToDevice(addressArduino: String): ResponseStateDomain<Boolean>{

        return repository.connectToDevice(addressArduino)

    }




}