package com.virtualworld.connectionBLE

class SetChangerStateUseCase(private val repository: BLERepository) {

    suspend fun changeState(stateNumber: String) {
        repository.sendStateCommand(stateNumber)

    }




}