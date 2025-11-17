package com.virtualworld.multiplatformiot.data.connectionBluetooth

import com.virtualworld.multiplatformiot.data.core.ResponseStateData
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothRepository
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseStateDomain
import kotlinx.coroutines.flow.Flow

expect class ImplBluetoothRepository() : BluetoothRepository {
    override suspend fun getPairedDevices(): ResponseStateData<List<ArduinoDomainModel>>
    override suspend fun connectToDevice(address: String): ResponseStateDomain<Boolean>
    override fun getAllStatesFlow(): Flow<ResponseStateDomain<ArduinoDomainModel>>
    override suspend fun sendStateCommand(stateNumber: String)

    override suspend fun disconnectFromDevice(address: String): Boolean
    override suspend fun isBluetoothEnabled(): Boolean

}
