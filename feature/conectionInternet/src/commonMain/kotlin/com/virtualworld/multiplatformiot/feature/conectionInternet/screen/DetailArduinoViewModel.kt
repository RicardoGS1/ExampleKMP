package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseStatesDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.GetArduinoUseCase
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DetailArduinoViewModel(private val getArduinoUseCase: GetArduinoUseCase) : ViewModel() {


    private val _arduinos = MutableStateFlow<ArduinosState<ArduinoDomainModel>>(ArduinosState.Loading)
    val arduinosState: StateFlow<ArduinosState<ArduinoDomainModel>> = _arduinos.asStateFlow()

    fun getArduino(arduinoName: String) {
        viewModelScope.launch {
            try {

                getArduinoUseCase.getArduino("usuario1", arduinoName).collect { arduino ->

                    when (arduino) {
                        is ResponseStatesDomain.Error -> {
                            ArduinosState.Error(exception = arduino.exception)
                        }

                        is ResponseStatesDomain.Loading -> {
                            ArduinosState.Loading
                        }

                        is ResponseStatesDomain.Success -> {

                            val arduinocorrect = ArduinoDomainModel(
                                name = arduino.result.name,
                                state1 = arduino.result.state1
                            )

                            _arduinos.update { ArduinosState.Success(arduinocorrect) }
                        }
                    }

                }

            } catch (e: Exception) {
                _arduinos.value = ArduinosState.Error(e)
            }
        }
    }

    fun updateState (key: String) {


        viewModelScope.launch {
            try {
                when (val currentState = _arduinos.value) {
                    is ArduinosState.Success -> {
                        val currentArduino = currentState.arduinos
                        getArduinoUseCase.updateArduinoState(currentArduino.name!!, key)
                    }
                    else -> {} // No hacemos nada si no estamos en estado Success
                }
            } catch (e: Exception) {
                _arduinos.value = ArduinosState.Error(e)
            }
        }
    }


}


