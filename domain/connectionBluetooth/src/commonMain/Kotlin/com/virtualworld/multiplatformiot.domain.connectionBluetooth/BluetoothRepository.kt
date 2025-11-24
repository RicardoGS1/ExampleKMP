package com.virtualworld.multiplatformiot.domain.connectionBluetooth

import com.virtualworld.multiplatformiot.data.core.ResponseStateData
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import kotlinx.coroutines.flow.Flow

interface BluetoothRepository {
    suspend fun getPairedDevices(): ResponseStateData<List<ArduinoDomainModel>>
    suspend fun connectToDevice(address: String): ResponseStateDomain<Boolean>
    fun getAllStatesFlow(): Flow<ResponseStateDomain<ArduinoDomainModel>>
    suspend fun sendStateCommand(stateNumber: String)

    suspend fun disconnectFromDevice(address: String): Boolean
    suspend fun isBluetoothEnabled(): Boolean
} 