package com.virtualworld.multiplatformiot.domain.conectionInternet.usecase

import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import com.virtualworld.multiplatformiot.domain.conectionInternet.mapper.mapperToDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.mapper.mapperToStateObjectDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ArduinoDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseState
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.StateObjectDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetArduinoUseCase (private val repositoryInternet: RepositoryInternet){

    fun getArduino(usuario:String, name:String): Flow<ResponseState<ArduinoDomain>> {

        return repositoryInternet.getArduinos(usuario, name).map {

            when (it) {

                is NetworkResponseState.Loading -> {
                    ResponseState.Loading
                }

                is NetworkResponseState.Error -> {
                    ResponseState.Error(it.exception)
                }

                is NetworkResponseState.Success -> {
                    ResponseState.Success(it.result.mapperToDomain())
                }

            }

        }
    }

    suspend fun updateArduinoState( arduinoName: String, key: String): ResponseState<StateObjectDomain> {

        val arduinoData = ArduinoData ( nameArduino = arduinoName, objetos = mapOf( key to StateObject()) )


        return when (val response = repositoryInternet.updateArduinoState( arduinoData)) {
            is NetworkResponseState.Loading -> ResponseState.Loading
            is NetworkResponseState.Error -> ResponseState.Error(response.exception)
            is NetworkResponseState.Success -> ResponseState.Success(response.result.mapperToStateObjectDomain())
        }
    }

}