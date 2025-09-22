package com.virtualworld.multiplatformiot.domain.connectionBluetooth


class GetPairedDevicesUseCase(
    private val bluetoothRepository: BluetoothRepository
) {
    suspend operator fun invoke(): ResponseState<List<BluetoothDeviceDomain>> {

        return try {

            val bluetoothDeviceDomainFilter = bluetoothRepository.getPairedDevices()
                .filter {
                    it.name.startsWith("Arduino", ignoreCase = true)

                }

            ResponseState.Success(bluetoothDeviceDomainFilter)

        } catch (e: Exception) {

            ResponseState.Error(e)

        }

//        return ResponseState.Success(
//            listOf(
//                BluetoothDeviceDomain("Arduino Uno", "00:11:22:AA:BB:CC"),
//                BluetoothDeviceDomain("My Phone", "11:22:33:DD:EE:FF"),
//                BluetoothDeviceDomain("arduino_mega", "22:33:44:GG:HH:II"),
//                BluetoothDeviceDomain("Headphones", "33:44:55:JJ:KK:LL"),
//                BluetoothDeviceDomain("ARDUINO NANO", "44:55:66:MM:NN:OO")
//            )
//        )


    }
} 