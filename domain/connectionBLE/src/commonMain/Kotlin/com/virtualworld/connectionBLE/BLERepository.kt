package com.virtualworld.connectionBLE

import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomain
import kotlinx.coroutines.flow.Flow

interface BLERepository {
    suspend fun getPairedDevices(): List<BLEDeviceDomain>
    suspend fun connectToDevice(address: String): ResponseState<Boolean>
    fun getAllStatesFlow(): Flow<ResponseState<ArduinoDomain>>
    suspend fun sendStateCommand(stateNumber: String)

    suspend fun disconnectFromDevice(address: String): Boolean
    suspend fun isBluetoothEnabled(): Boolean
} 