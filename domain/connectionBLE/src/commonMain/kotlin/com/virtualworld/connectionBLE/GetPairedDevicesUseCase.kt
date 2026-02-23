package com.virtualworld.connectionBLE

import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel


class GetPairedDevicesUseCase(
    private val bluetoothRepository: BLERepository
) {
    suspend operator fun invoke(): ResponseState<List<ArduinoDomainModel>> {

        return try {

            val allPairedDevices = bluetoothRepository.getPairedDevices()

            if (allPairedDevices.isEmpty()) {
                return ResponseState.Error(Exception("No se encontraron dispositivos Bluetooth emparejados. Verifique el permiso bluetooth."))
            }

            val filteredDevices = allPairedDevices.filter {
                it.name.startsWith("Arduino", ignoreCase = true) ||
                        it.name.startsWith("desktop", ignoreCase = true) ||
                        it.name.contains("hc-05", ignoreCase = true)
            }

//            if (filteredDevices.isEmpty()) {
//                return ResponseState.Error(Exception("Ninguno de los dispositivos emparejados es compatible."))
//            }

            ResponseState.Success(filteredDevices)

        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }
} 