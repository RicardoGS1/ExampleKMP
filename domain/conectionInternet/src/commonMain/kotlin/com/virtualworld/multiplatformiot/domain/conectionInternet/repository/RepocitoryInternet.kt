package com.virtualworld.multiplatformiot.domain.conectionInternet.repository

import com.virtualworld.multiplatformiot.data.core.Arduino
import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import kotlinx.coroutines.flow.Flow

interface RepocitoryInternet {

    fun getAllArduinos(usuario:String): Flow<NetworkResponseState<List<Arduino>>>



}