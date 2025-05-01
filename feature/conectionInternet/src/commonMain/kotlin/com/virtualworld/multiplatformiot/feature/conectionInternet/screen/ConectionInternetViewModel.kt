package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseState
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.UseCaseInternet
import kotlinx.coroutines.launch

class ConectionInternetViewModel( private val useCaseInternet: UseCaseInternet) : ViewModel() {


//    private val _arduinos = MutableStateFlow<List<String>>(emptyList())
//    val arduino: StateFlow<List<String>> = _arduinos.asStateFlow()


    init {
        getAllArduinos()
    }

    fun getAllArduinos(){

        viewModelScope.launch {



                useCaseInternet.getAllArduinos("usuario2").collect{ arduinos->

                    when (arduinos){
                        is ResponseState.Error -> {
                            println("error")
                        }
                        is ResponseState.Loading -> {println("loading")}
                        is ResponseState.Success -> println(arduinos)
                    }

                }


        }



    }



}