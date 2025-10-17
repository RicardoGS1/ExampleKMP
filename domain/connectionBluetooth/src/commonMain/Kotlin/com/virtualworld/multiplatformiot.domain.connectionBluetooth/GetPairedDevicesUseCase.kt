package com.virtualworld.multiplatformiot.domain.connectionBluetooth


class GetPairedDevicesUseCase(
    private val bluetoothRepository: BluetoothRepository
) {
    suspend operator fun invoke(): ResponseState<List<BluetoothDeviceDomain>> {

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

            ResponseState.Success(allPairedDevices)

        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }
} 