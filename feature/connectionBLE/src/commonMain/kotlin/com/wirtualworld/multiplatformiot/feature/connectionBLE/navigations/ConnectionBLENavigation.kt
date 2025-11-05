package com.wirtualworld.multiplatformiot.feature.connectionBLE.navigations

import kotlinx.serialization.Serializable


@Serializable
sealed class ConnectionBLENavigation(val route: String) {

    @Serializable
    data object ConnectionBluetoothLE : ConnectionBLENavigation("connectionBLE")

    @Serializable
    data class DetailArduino(val arduinoName: String, val arduinoAddress: String) :
        ConnectionBLENavigation("detailArduino/{$ARDUINO_NAME_ARG}/{$ARDUINO_ADDRESS_ARG}") {
        fun createRoute(arduinoName: String,arduinoAddress: String) = "detailArduino/$arduinoName/$arduinoAddress"
    }


    companion object {
        const val ARDUINO_NAME_ARG = "arduinoName"
        const val ARDUINO_ADDRESS_ARG = "arduinoAddress"
    }
}