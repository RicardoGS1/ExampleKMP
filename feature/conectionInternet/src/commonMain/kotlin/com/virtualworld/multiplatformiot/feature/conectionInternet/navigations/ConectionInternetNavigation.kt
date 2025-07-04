package com.virtualworld.multiplatformiot.feature.conectionInternet.navigations

import kotlinx.serialization.Serializable

@Serializable
sealed class ConectionInternetNavigation(val route: String) {

    @Serializable
    data object ConectionInternet : ConectionInternetNavigation("conectionInternet")

    @Serializable
    data class DetailArduino(val arduinoName: String) : ConectionInternetNavigation("detailArduino/{$ARDUINO_NAME_ARG}") {
        fun createRoute(arduinoName: String) = "detailArduino/$arduinoName"
    }

    @Serializable
    data object AddArduino : ConectionInternetNavigation("addArduino")

    companion object {
        const val ARDUINO_NAME_ARG = "arduinoName"
    }
} 