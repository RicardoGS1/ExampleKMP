package com.virtualworld.multiplatformiot.data.connectionBluetooth

import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothDeviceDomain
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothRepository
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseState
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import kotlinx.coroutines.flow.Flow

actual class ImplBluetoothRepository actual constructor() :
    BluetoothRepository {
    actual override suspend fun getPairedDevices(): List<ArduinoDomainModel> {
        return emptyList()
    }

    actual override suspend fun connectToDevice(address: String): ResponseState<Boolean> {
        TODO("Not yet implemented")
    }

    actual override fun getAllStatesFlow(): Flow<ResponseState<ArduinoDomainModel>> {
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