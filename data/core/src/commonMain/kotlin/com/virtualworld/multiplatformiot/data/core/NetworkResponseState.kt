package com.virtualworld.multiplatformiot.data.core

sealed class NetworkResponseState<out T : Any> {

    data class Success<out T : Any>(val result: T) : NetworkResponseState<T>()

    data class Error(val exception: Exception) : NetworkResponseState<Nothing>()
}