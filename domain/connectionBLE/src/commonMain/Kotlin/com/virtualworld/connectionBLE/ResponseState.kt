package com.virtualworld.connectionBLE

sealed class ResponseState<out T : Any> {

    data class Success<out T : Any>(val result: T) : ResponseState<T>()

    data class Error(val exception: Exception) : ResponseState<Nothing>()
}