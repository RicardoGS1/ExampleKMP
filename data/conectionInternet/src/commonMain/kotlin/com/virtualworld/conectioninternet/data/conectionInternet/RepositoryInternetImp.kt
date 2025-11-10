package com.virtualworld.conectioninternet.data.conectionInternet

import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import kotlinx.coroutines.flow.Flow

class RepositoryInternetImp ( private val remoteDataSource: RemoteDataSource): RepositoryInternet {


    override fun getAllArduinos(usuario: String): Flow<NetworkResponseState<List<ArduinoData>>> {
       return remoteDataSource.getAllArduino(usuario)
    }

    override fun getArduinos(usuario: String, name: String): Flow<NetworkResponseState<ArduinoData>> {
        return remoteDataSource.getArduino(usuario, name)
    }

    override suspend fun updateArduinoState(
        arduinoData: ArduinoData,
    ): NetworkResponseState<StateObject> {
        return remoteDataSource.updateArduinoState( arduinoData)
    }

    override suspend fun addArduino(arduino: ArduinoDomainModel) {
        remoteDataSource.addArduino(arduino)
    }

    override fun getArduinoActivate(usuario: String): Flow<NetworkResponseState<List<ArduinoData>>> {
       return remoteDataSource.getAllArduino(usuario)
    }

}