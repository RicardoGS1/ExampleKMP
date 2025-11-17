package com.virtualworld.multiplatformiot.domain.connectionBluetooth

sealed class ResponseStateDomain<out T : Any> {

    data class Success<out T : Any>(val result: T) : ResponseStateDomain<T>()

    data class Error(val exception: Exception) : ResponseStateDomain<Nothing>()
}