package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseStatesDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.GetArduinoDetailUseCase
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.SetStateArduinoUseCase
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DetailArduinoViewModel(
    private val getArduinoDetailUseCase: GetArduinoDetailUseCase,
    private val setStateArduinoUseCase: SetStateArduinoUseCase
) :
    ViewModel() {


    private val _arduinos =
        MutableStateFlow<ArduinosState<ArduinoDomainModel>>(ArduinosState.Loading)
    val arduinosState: StateFlow<ArduinosState<ArduinoDomainModel>> = _arduinos.asStateFlow()

    fun getDetailArduino(arduinoName: String) {

        _arduinos.update { ArduinosState.Loading }

        viewModelScope.launch {

            getArduinoDetailUseCase.getArduino("usuario1", arduinoName).collect { arduino ->

                when (arduino) {
                    is ResponseStatesDomain.Error -> {
                        ArduinosState.Error(exception = arduino.exception)
                    }

                    is ResponseStatesDomain.Success -> {
                        val arduinocorrect = ArduinoDomainModel(
                            name = arduino.result.name,
                            address = arduino.result.address,
                            states = arduino.result.states
                        )
                        _arduinos.update { ArduinosState.Success(arduinocorrect) }
                    }
                }

            }
        }
    }

    fun updateState(key: String) {

        _arduinos.update { ArduinosState.Loading }

        viewModelScope.launch {

            if (_arduinos.value is ArduinosState.Success) {
                val currentArduino =
                    (_arduinos.value as ArduinosState.Success<ArduinoDomainModel>).arduinos
                setStateArduinoUseCase.updateArduinoState("usuario1",currentArduino.name, key)
            }
        }
    }


}


