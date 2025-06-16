package com.virtualworld.multiplatformiot.domain.conectionInternet.repository

import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import kotlinx.coroutines.flow.Flow

interface RepositoryInternet {

    fun getAllArduinos(usuario:String): Flow<NetworkResponseState<List<ArduinoData>>>

    fun getArduinos(usuario:String, name:String): Flow<NetworkResponseState<ArduinoData>>

    suspend fun updateArduinoState(usuario: String, arduinoName: String, key: String, newValue: Boolean): NetworkResponseState<StateObject>

}