package com.virtualworld.multiplatformiot.domain.conectionInternet.usecase

import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import com.virtualworld.multiplatformiot.domain.conectionInternet.mapper.mapperToDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.mapper.mapperToStateObjectDomain
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseStatesDomain
import com.virtualworld.multiplatformiot.domain.core.models.StateObjectDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetArduinoDetailUseCase (private val repositoryInternet: RepositoryInternet){

    fun getArduino(usuario:String, name:String): Flow<ResponseStatesDomain<ArduinoDomainModel>> {

        return repositoryInternet.getArduinos(usuario, name).map {

            when (it) {

                is NetworkResponseState.Error -> {
                    ResponseStatesDomain.Error(it.exception)
                }

                is NetworkResponseState.Success -> {
                    ResponseStatesDomain.Success(it.result.mapperToDomain())
                }

            }

        }
    }



}