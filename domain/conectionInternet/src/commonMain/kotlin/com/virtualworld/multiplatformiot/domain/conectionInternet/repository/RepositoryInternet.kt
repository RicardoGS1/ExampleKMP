package com.virtualworld.multiplatformiot.domain.conectionInternet.repository

import com.virtualworld.multiplatformiot.data.core.dto.Arduino
import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import kotlinx.coroutines.flow.Flow

interface RepositoryInternet {

    fun getAllArduinos(usuario:String): Flow<NetworkResponseState<List<Arduino>>>



}