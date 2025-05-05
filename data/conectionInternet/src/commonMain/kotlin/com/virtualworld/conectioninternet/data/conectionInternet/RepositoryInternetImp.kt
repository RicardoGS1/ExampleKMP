package com.virtualworld.conectioninternet.data.conectionInternet

import com.virtualworld.multiplatformiot.data.core.dto.Arduino
import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import kotlinx.coroutines.flow.Flow

class RepositoryInternetImp ( private val remoteDataSource: RemoteDataSource): RepositoryInternet {


    override fun getAllArduinos(usuario: String): Flow<NetworkResponseState<List<Arduino>>> {
       return remoteDataSource.getAllArduino(usuario)
    }


}