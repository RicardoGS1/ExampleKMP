package com.virtualworld.multiplatformiot.domain.connectionBluetooth

class SetChangerStateUseCase(private val repository: BluetoothRepository) {

    suspend fun changeState(stateNumber: String) {
        repository.sendStateCommand(stateNumber)

    }




}