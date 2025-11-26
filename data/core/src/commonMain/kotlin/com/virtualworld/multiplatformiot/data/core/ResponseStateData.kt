package com.virtualworld.multiplatformiot.data.core

sealed class ResponseStateData<out T : Any> {

    data class Success<out T : Any>(val result: T) : ResponseStateData<T>()

    data class Error(val exception: Exception) : ResponseStateData<Nothing>()
}