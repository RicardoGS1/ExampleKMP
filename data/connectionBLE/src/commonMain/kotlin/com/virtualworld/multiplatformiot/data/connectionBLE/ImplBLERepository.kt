package com.virtualworld.multiplatformiot.data.connectionBLE

import com.virtualworld.connectionBLE.BLEDeviceDomain
import com.virtualworld.connectionBLE.BLERepository
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomain
import com.virtualworld.connectionBLE.ResponseState
import kotlinx.coroutines.flow.Flow

expect class ImplBLERepository() : BLERepository {
    override suspend fun getPairedDevices(): List<BLEDeviceDomain>
    override suspend fun connectToDevice(address: String): ResponseState<Boolean>
    override fun getAllStatesFlow(): Flow<ResponseState<ArduinoDomain>>
    override suspend fun sendStateCommand(stateNumber: String)

    override suspend fun disconnectFromDevice(address: String): Boolean
    override suspend fun isBluetoothEnabled(): Boolean

}
