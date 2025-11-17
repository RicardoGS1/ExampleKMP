package com.virtualworld.multiplatformiot.domain.conectionInternet.usecase


import com.virtualworld.multiplatformiot.data.core.ResponseStateData
import com.virtualworld.multiplatformiot.domain.conectionInternet.mapper.mapperToDomain
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseStatesDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseStatesDomain.*
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet

class GetListArduinosUseCase(private val repositoryInternet: RepositoryInternet) {

    suspend operator fun invoke(usuario: String): ResponseStatesDomain<List<ArduinoDomainModel>> {

        val listArduinos = repositoryInternet.getAllArduinos(usuario)

        return when (listArduinos) {

            is ResponseStateData.Error -> {
                Error(listArduinos.exception)
            }

            is ResponseStateData.Success -> {
                Success(listArduinos.result.map { it.mapperToDomain() })
            }

        }

    }

}