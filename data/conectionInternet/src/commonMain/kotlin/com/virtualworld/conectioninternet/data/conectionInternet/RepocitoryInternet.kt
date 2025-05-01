package com.virtualworld.conectioninternet.data.conectionInternet

import kotlinx.coroutines.flow.Flow

interface RepocitoryInternet {

    fun getAllArduinos(usuario:String): Flow<NetworkResponseState<List<Arduino>>>



}