package com.virtualworld.multiplatformiot.data.connectionBluetooth

import com.virtualworld.multiplatformiot.data.core.ResponseStateData
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothRepository
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseStateDomain
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import kotlinx.coroutines.flow.Flow

actual class ImplBluetoothRepository actual constructor() :
    BluetoothRepository {
    actual override suspend fun getPairedDevices(): ResponseStateData<List<ArduinoDomainModel>> {
        return ResponseStateData.Error(Exception("Esta conexión actualmente no esta disponible para IOS"))
    }

    actual override suspend fun connectToDevice(address: String): ResponseStateDomain<Boolean> {
        TODO("Not yet implemented")
    }

    actual override fun getAllStatesFlow(): Flow<ResponseStateDomain<ArduinoDomainModel>> {
        TODO("Not yet implemented")
    }

    actual override suspend fun sendStateCommand(stateNumber: String) {
    }

    actual override suspend fun disconnectFromDevice(address: String): Boolean {
        TODO("Not yet implemented")
    }

    actual override suspend fun isBluetoothEnabled(): Boolean {
        TODO("Not yet implemented")
    }
}