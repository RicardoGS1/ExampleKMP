package com.virtualworld.multiplatformiot.domain.connectionBluetooth

import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import kotlinx.coroutines.flow.Flow

interface BluetoothRepository {
    suspend fun getPairedDevices(): List<ArduinoDomainModel>
    suspend fun connectToDevice(address: String): ResponseState<Boolean>
    fun getAllStatesFlow(): Flow<ResponseState<ArduinoDomainModel>>
    suspend fun sendStateCommand(stateNumber: String)

    suspend fun disconnectFromDevice(address: String): Boolean
    suspend fun isBluetoothEnabled(): Boolean
} 