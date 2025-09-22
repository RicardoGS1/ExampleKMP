package com.virtualworld.multiplatformiot.domain.connectionBluetooth

import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomain
import kotlinx.coroutines.flow.Flow

interface BluetoothRepository {
    suspend fun getPairedDevices(): List<BluetoothDeviceDomain>
    suspend fun connectToDevice(address: String): ResponseState<Boolean>
    fun getAllStatesFlow(): Flow<ResponseState<ArduinoDomain>>
    suspend fun sendStateCommand(stateNumber: String)

    suspend fun disconnectFromDevice(address: String): Boolean
    suspend fun isBluetoothEnabled(): Boolean
} 