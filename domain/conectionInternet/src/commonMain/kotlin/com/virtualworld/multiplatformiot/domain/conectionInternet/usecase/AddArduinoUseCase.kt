package com.virtualworld.multiplatformiot.domain.conectionInternet.usecase

import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomain
import com.virtualworld.multiplatformiot.domain.core.models.StateObjectDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet

class AddArduinoUseCase(private val repositoryInternet: RepositoryInternet) {

    suspend fun addArduino(name: String, states: Map<String, Boolean>) {

        val mapStates = mutableMapOf<String, StateObjectDomain>()

        states.forEach {
            mapStates[it.key] = StateObjectDomain(it.key, it.value)
        }

        val addArduino = ArduinoDomain(name, state1 = mapStates)

        repositoryInternet.addArduino(addArduino)

    }
} 