package com.virtualworld.multiplatformiot.domain.conectionInternet.usecase

import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseState
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetArduinoActivateUseCase (private val repositoryInternet: RepositoryInternet) {

    fun getArduinoActivate(usuario: String): Flow<ResponseState<Map<String,Int>>> {

        return repositoryInternet.getArduinoActivate(usuario).map {

            when (it) {

                is NetworkResponseState.Loading -> {
                    ResponseState.Loading
                }

                is NetworkResponseState.Error -> {
                    ResponseState.Error(it.exception)
                }

                is NetworkResponseState.Success -> {
                    ResponseState.Success(contArduinoActivate(it.result))
                }

            }

        }
    }


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
