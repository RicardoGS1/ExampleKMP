package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ArduinoDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseState
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.GetArduinoUseCase
import com.virtualworld.multiplatformiot.feature.conectionInternet.models.ArduinosState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.toMap
import kotlin.collections.toMutableMap


class DetailArduinoViewModel(private val getArduinoUseCase: GetArduinoUseCase) : ViewModel() {


    private val _arduinos = MutableStateFlow<ArduinosState<ArduinoDomain>>(ArduinosState.Loading)
    val arduinosState: StateFlow<ArduinosState<ArduinoDomain>> = _arduinos.asStateFlow()

    fun getArduino(arduinoName: String) {
        viewModelScope.launch {
            try {

                getArduinoUseCase.getArduino("usuario1", arduinoName).collect { arduino ->

                    when (arduino) {
                        is ResponseState.Error -> {
                            ArduinosState.Error(exception = arduino.exception)
                        }

                        is ResponseState.Loading -> {
                            ArduinosState.Loading
                        }

                        is ResponseState.Success -> {

                            val arduinocorrect = ArduinoDomain(
                                name = arduino.result.name,
                                state1 = mapOf( arduino.result.state1.entries.first().toPair() , arduino.result.state2.entries.first().toPair(),arduino.result.state3.entries.first().toPair())
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

    fun updateState(key: String, newValue: Boolean) {


    }


}


