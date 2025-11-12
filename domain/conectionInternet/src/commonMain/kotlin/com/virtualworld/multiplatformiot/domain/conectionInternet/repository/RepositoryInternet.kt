package com.virtualworld.multiplatformiot.domain.conectionInternet.repository

import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import kotlinx.coroutines.flow.Flow

interface RepositoryInternet {

    suspend fun getAllArduinos(usuario: String): NetworkResponseState<List<ArduinoData>>

    fun getArduinos(usuario: String, name: String): Flow<NetworkResponseState<ArduinoData>>

    suspend fun updateArduinoState(usuario:String, arduinoData: ArduinoData): NetworkResponseState<StateObject>

    suspend fun addArduino(arduino: ArduinoDomainModel)

    // fun getArduinoActivate (usuario: String) : Flow<NetworkResponseState<List<ArduinoData>>>

}