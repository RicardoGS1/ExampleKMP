package com.virtualworld.multiplatformiot.domain.conectionInternet.usecase


import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.domain.conectionInternet.mapper.mapperToDomain
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseStatesDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListArduinosUseCase(private val repositoryInternet: RepositoryInternet) {



    suspend operator fun invoke(usuario: String): ResponseStatesDomain<List<ArduinoDomainModel>> {

        val listArduinos = repositoryInternet.getAllArduinos(usuario)

        return when (listArduinos) {
            is NetworkResponseState.Loading -> TODO()
            is NetworkResponseState.Error -> {
                ResponseStatesDomain.Error(listArduinos.exception)
            }

            is NetworkResponseState.Success -> {
                ResponseStatesDomain.Success(listArduinos.result.map { it.mapperToDomain() })
            }
        }

    }

}