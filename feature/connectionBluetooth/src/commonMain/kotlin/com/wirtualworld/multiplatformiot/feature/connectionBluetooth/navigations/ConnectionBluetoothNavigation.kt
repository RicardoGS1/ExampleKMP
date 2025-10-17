package com.wirtualworld.multiplatformiot.feature.connectionBluetooth.navigations

import kotlinx.serialization.Serializable


@Serializable
sealed class ConnectionBluetoothNavigation(val route: String) {

    @Serializable
    data object ConnectionBluetooth : ConnectionBluetoothNavigation("connectionBluetooth")

    @Serializable
    data class DetailArduino(val arduinoName: String, val arduinoAddress: String) :
        ConnectionBluetoothNavigation("detailArduino/{$ARDUINO_NAME_ARG}/{$ARDUINO_ADDRESS_ARG}") {
        fun createRoute(arduinoName: String,arduinoAddress: String) = "detailArduino/$arduinoName/$arduinoAddress"
    }

    //
//    @Serializable
//    data object AddArduino : ConnectionBluetoothNavigation("addArduino")
//
    companion object {
        const val ARDUINO_NAME_ARG = "arduinoName"
        const val ARDUINO_ADDRESS_ARG = "arduinoAddress"
    }
}