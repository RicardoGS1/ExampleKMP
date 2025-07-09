package com.virtualworld.multiplatformiot.feature.menu


sealed class StateScreenMenu<out T> {

    data class Error(val exception: Exception) : StateScreenMenu<Nothing>()

    data object Loading : StateScreenMenu<Nothing>()

    data class Success<out T>(val arduinos: T) : StateScreenMenu<T>()
}
