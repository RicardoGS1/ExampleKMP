package com.virtualworld.multiplatformiot.data.connectionBluetooth

import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothDeviceDomain
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothRepository
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseState
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomain
import kotlinx.coroutines.flow.Flow

actual class ImplBluetoothRepository  :
    BluetoothRepository {

    actual override suspend fun getPairedDevices(): List<BluetoothDeviceDomain> {
        TODO("Not yet implemented")
    }

    actual override suspend fun connectToDevice(address: String): ResponseState<Boolean> {
        TODO("Not yet implemented")
    }


    actual override fun getAllStatesFlow(): Flow<ResponseState<ArduinoDomain>> {
        TODO("Not yet implemented")
    }

    actual override suspend fun sendStateCommand(stateNumber: String) {
        TODO("Not yet implemented")
    }

    actual override suspend fun disconnectFromDevice(address: String): Boolean {
        TODO("Not yet implemented")
    }

    actual override suspend fun isBluetoothEnabled(): Boolean {
        TODO("Not yet implemented")
    }
}