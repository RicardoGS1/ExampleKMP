package com.virtualworld.multiplatformiot.domain.conectionInternet.usecase


import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.domain.conectionInternet.mapper.mapperToDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ArduinoDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseState
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UseCaseInternet(private val repositoryInternet: RepositoryInternet) {

    fun getAllArduinos(usuario: String): Flow<ResponseState<List<ArduinoDomain>>> {
        return repositoryInternet.getAllArduinos(usuario).map {

            when (it) {

                is NetworkResponseState.Loading -> {
                    ResponseState.Loading
                }

                is NetworkResponseState.Error -> {
                    ResponseState.Error(it.exception)
                }

                is NetworkResponseState.Success -> {
                    ResponseState.Success(it.result.map { it.mapperToDomain() })
                }

            }


        }
    }


}