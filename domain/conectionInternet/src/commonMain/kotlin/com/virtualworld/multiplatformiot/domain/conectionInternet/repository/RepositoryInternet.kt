package com.virtualworld.multiplatformiot.domain.conectionInternet.repository

import com.virtualworld.multiplatformiot.data.core.ResponseStateData
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import kotlinx.coroutines.flow.Flow

interface RepositoryInternet {

    suspend fun getAllArduinos(): ResponseStateData<List<ArduinoData>>

    fun getArduinos(name: String): Flow<ResponseStateData<ArduinoData>>

    suspend fun updateArduinoState(arduinoData: ArduinoData): ResponseStateData<StateObject>

    suspend fun addArduino(arduino: ArduinoDomainModel)

    // fun getArduinoActivate (usuario: String) : Flow<NetworkResponseState<List<ArduinoData>>>

}