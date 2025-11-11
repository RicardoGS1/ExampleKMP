package com.virtualworld.multiplatformiot.domain.conectionInternet.model

sealed class ResponseStatesDomain<out T : Any> {

    object Loading : ResponseStatesDomain<Nothing>()

    data class Success<out T : Any>(val result: T) : ResponseStatesDomain<T>()

    data class Error(val exception: Exception) : ResponseStatesDomain<Nothing>()
}