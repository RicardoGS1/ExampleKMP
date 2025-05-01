package com.virtualworld.conectioninternet.data.conectionInternet

import com.virtualworld.multiplatformiot.data.core.Arduino
import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepocitoryInternet
import kotlinx.coroutines.flow.Flow

class RepositoryInternetImp ( private val remoteDataSource: RemoteDataSource): RepocitoryInternet {


    override fun getAllArduinos(usuario: String): Flow<NetworkResponseState<List<Arduino>>> {
       return remoteDataSource.getAllArduino(usuario)
    }


}