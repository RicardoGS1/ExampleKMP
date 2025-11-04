package com.virtualworld.connectionBLE

class ConnectToArduinoUseCase(private val repository: BLERepository) {

    suspend fun connectToDevice(addressArduino: String): ResponseState<Boolean>{


       return repository.connectToDevice(addressArduino)

    }




}