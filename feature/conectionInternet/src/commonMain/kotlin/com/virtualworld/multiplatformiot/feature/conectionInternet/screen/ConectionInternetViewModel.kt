package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ArduinoDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseState
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.UseCaseInternet
import com.virtualworld.multiplatformiot.feature.conectionInternet.models.ArduinosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConectionInternetViewModel(private val useCaseInternet: UseCaseInternet) : ViewModel() {


    private val _arduinos =
        MutableStateFlow<ArduinosState<List<ArduinoDomain>>>(ArduinosState.Loading)
    val arduinosState: StateFlow<ArduinosState<List<ArduinoDomain>>> = _arduinos.asStateFlow()


    init {
        getAllArduinos()
    }

    private fun getAllArduinos() {

        viewModelScope.launch {


            useCaseInternet.getAllArduinos("usuario1").collect { arduinos ->

                when (arduinos) {
                    is ResponseState.Error -> {
                        ArduinosState.Error(exception = arduinos.exception)
                    }

                    is ResponseState.Loading -> {
                        ArduinosState.Loading
                    }

                    is ResponseState.Success -> _arduinos.update { ArduinosState.Success(arduinos.result) }
                }

            }


        }


    }


}