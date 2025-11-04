package com.virtualworld.multiplatformiot.data.connectionBLE

import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BLEDeviceDomain
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BLERepository
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseState
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomain
import kotlinx.coroutines.flow.Flow

actual class ImplBLERepository actual constructor() :
    BLERepository {
    actual override suspend fun getPairedDevices(): List<BLEDeviceDomain> {
        TODO("Not yet implemented")
    }

    actual override suspend fun connectToDevice(address: String): ResponseState<Boolean> {
        TODO("Not yet implemented")
    }

    actual override fun getAllStatesFlow(): Flow<ResponseState<ArduinoDomain>> {
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