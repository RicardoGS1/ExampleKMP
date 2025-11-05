package com.wirtualworld.multiplatformiot.feature.conectionLocal.navigations

import kotlinx.serialization.Serializable

@Serializable
sealed class ConectionLocalNavigation(val route: String) {

    @Serializable
    data object ConectionLocal : ConectionLocalNavigation("conectionLocal")

//    @Serializable
//    data class DetailArduino(val arduinoName: String) : ConectionLocalNavigation("detailArduino/{$ARDUINO_NAME_ARG}") {
//        fun createRoute(arduinoName: String) = "detailArduino/$arduinoName"
//    }

//    @Serializable
//    data object AddArduino : ConectionLocalNavigation("addArduino")
//
//    companion object {
//        const val ARDUINO_NAME_ARG = "arduinoName"
//    }
} 