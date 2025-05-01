package com.virtualworld.multiplatformiot.domain.conectionInternet

import com.virtualworld.conectioninternet.data.conectionInternet.Arduino
import com.virtualworld.conectioninternet.data.conectionInternet.NetworkResponseState
import com.virtualworld.conectioninternet.data.conectionInternet.RepocitoryInternet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UseCaseInternet(private val repositoryInternet: RepocitoryInternet) {

    fun getAllArduinos (usuario: String) : Flow<ResponseState<List<Arduino>>> {
       return repositoryInternet.getAllArduinos(usuario).map {

           when(it){

               is NetworkResponseState.Loading -> { ResponseState.Loading}
               is NetworkResponseState.Error -> { ResponseState.Error(it.exception)}
               is NetworkResponseState.Success -> { ResponseState.Success(it.result)}


           }




       }
    }



}