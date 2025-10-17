package com.virtualworld.multiplatformiot.data.connectionBluetooth

import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomain
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothDeviceDomain
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothRepository
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseState
import kotlinx.coroutines.flow.Flow

expect class ImplBluetoothRepository() : BluetoothRepository {
    override suspend fun getPairedDevices(): List<BluetoothDeviceDomain>
    override suspend fun connectToDevice(address: String): ResponseState<Boolean>
    override fun getAllStatesFlow(): Flow<ResponseState<ArduinoDomain>>
    override suspend fun sendStateCommand(stateNumber: String)

    override suspend fun disconnectFromDevice(address: String): Boolean
    override suspend fun isBluetoothEnabled(): Boolean

}
