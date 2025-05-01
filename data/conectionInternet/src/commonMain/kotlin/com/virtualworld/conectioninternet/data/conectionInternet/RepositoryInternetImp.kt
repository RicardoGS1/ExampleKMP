package com.virtualworld.conectioninternet.data.conectionInternet

import kotlinx.coroutines.flow.Flow

class RepositoryInternetImp ( private val remoteDataSource: RemoteDataSource): RepocitoryInternet {


    override fun getAllArduinos(usuario: String): Flow<NetworkResponseState<List<Arduino>>> {
       return remoteDataSource.getAllArduino(usuario)
    }


}