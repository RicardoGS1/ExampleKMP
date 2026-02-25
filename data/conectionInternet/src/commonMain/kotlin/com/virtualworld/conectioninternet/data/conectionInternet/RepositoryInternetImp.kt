package com.virtualworld.conectioninternet.data.conectionInternet

import com.virtualworld.multiplatformiot.data.core.ResponseStateData
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import kotlinx.coroutines.flow.Flow

class RepositoryInternetImp ( private val remoteDataSource: RemoteDataSource): RepositoryInternet {


    override suspend fun getAllArduinos(): ResponseStateData<List<ArduinoData>> {
       return remoteDataSource.getAllArduino()
    }

    override fun getArduinos(name: String): Flow<ResponseStateData<ArduinoData>> {
        return remoteDataSource.getArduino(name)
    }

    override suspend fun updateArduinoState(arduinoData: ArduinoData): ResponseStateData<StateObject> {
        return remoteDataSource.updateArduinoState(arduinoData)
    }

    override suspend fun addArduino(arduino: ArduinoDomainModel) {
        remoteDataSource.addArduino(arduino)
    }

//    override fun getArduinoActivate(usuario: String): Flow<NetworkResponseState<List<ArduinoData>>> {
//       return remoteDataSource.getAllArduino(usuario)
//    }

}