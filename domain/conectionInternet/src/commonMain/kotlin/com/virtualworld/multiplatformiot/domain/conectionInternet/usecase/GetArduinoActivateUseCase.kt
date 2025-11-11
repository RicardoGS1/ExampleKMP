package com.virtualworld.multiplatformiot.domain.conectionInternet.usecase

import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseStatesDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetArduinoActivateUseCase (private val repositoryInternet: RepositoryInternet) {

//    fun getArduinoActivate(usuario: String): Flow<ResponseStatesDomain<Map<String,Int>>> {
//
//        return repositoryInternet.getArduinoActivate(usuario).map {
//
//            when (it) {
//
//                is NetworkResponseState.Loading -> {
//                    ResponseStatesDomain.Loading
//                }
//
//                is NetworkResponseState.Error -> {
//                    ResponseStatesDomain.Error(it.exception)
//                }
//
//                is NetworkResponseState.Success -> {
//                    ResponseStatesDomain.Success(contArduinoActivate(it.result))
//                }
//
//            }
//
//        }.flowOn(Dispatchers.IO)
//    }


    private fun contArduinoActivate(result: List<ArduinoData>): Map<String,Int>  {


       val mapActivate : MutableMap<String,Int> = mutableMapOf("activate" to 0, "deactivate" to 0)

        var a = 0
        var d = 0

        result.forEach {



            if(it.active == true) {
                a++
                mapActivate["activate"] = a
            }
            else{

                d++
                mapActivate["deactivate"] = d
            }
        }

        return  mapActivate


    }




}
