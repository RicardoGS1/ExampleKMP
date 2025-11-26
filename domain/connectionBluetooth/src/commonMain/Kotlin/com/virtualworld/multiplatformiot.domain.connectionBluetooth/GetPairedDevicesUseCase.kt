package com.virtualworld.multiplatformiot.domain.connectionBluetooth

import com.virtualworld.multiplatformiot.data.core.ResponseStateData
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel


class GetPairedDevicesUseCase(
    private val bluetoothRepository: BluetoothRepository
) {
    suspend operator fun invoke(): ResponseStateDomain<List<ArduinoDomainModel>> {

        val allPairedDevices = bluetoothRepository.getPairedDevices()

        return when (allPairedDevices) {

            is ResponseStateData.Error -> {
                ResponseStateDomain.Error(allPairedDevices.exception)
            }

            is ResponseStateData.Success<List<ArduinoDomainModel>> -> {

                val filteredDevices = allPairedDevices.result.filter {
                    it.name.startsWith("Arduino", ignoreCase = true) ||
                            it.name.startsWith("desktop", ignoreCase = true) ||
                            it.name.contains("hc-05", ignoreCase = true)
                }

                if (!filteredDevices.isEmpty())
                    ResponseStateDomain.Success(filteredDevices)
                else
                    ResponseStateDomain.Error(Exception("Los dispositivos encontrados no son compatibles o no estan correctamente configurados"))
            }
        }

    }
} 