package com.virtualworld.multiplatformiot.domain.conectionInternet.usecase

import com.virtualworld.multiplatformiot.data.core.ResponseStateData
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import com.virtualworld.multiplatformiot.domain.conectionInternet.mapper.mapperToStateObjectDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseStatesDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import com.virtualworld.multiplatformiot.domain.core.models.StateObjectDomain

class SetStateArduinoUseCase(private val repositoryInternet: RepositoryInternet) {

    suspend fun updateArduinoState(arduinoName: String, key: String): ResponseStatesDomain<StateObjectDomain> {

        val arduinoData = ArduinoData(nameArduino = arduinoName, objetos = mapOf(key to StateObject()))

        return when (val response = repositoryInternet.updateArduinoState(arduinoData)) {
            is ResponseStateData.Error -> ResponseStatesDomain.Error(response.exception)
            is ResponseStateData.Success -> ResponseStatesDomain.Success(response.result.mapperToStateObjectDomain())
        }
    }
}