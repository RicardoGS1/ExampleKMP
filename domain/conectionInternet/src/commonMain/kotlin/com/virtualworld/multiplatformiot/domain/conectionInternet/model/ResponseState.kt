package com.virtualworld.multiplatformiot.domain.conectionInternet.model

sealed class ResponseState<out T : Any> {

    object Loading : ResponseState<Nothing>()

    data class Success<out T : Any>(val result: T) : ResponseState<T>()

    data class Error(val exception: Exception) : ResponseState<Nothing>()
}