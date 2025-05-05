package com.virtualworld.multiplatformiot.feature.conectionInternet.models



sealed class ArduinosState<out T> {

    data class Error (val exception: Exception) : ArduinosState<Nothing>()

    data object Loading : ArduinosState<Nothing>()

    data class Success <out T> (val arduinos: T) : ArduinosState<T>()
}