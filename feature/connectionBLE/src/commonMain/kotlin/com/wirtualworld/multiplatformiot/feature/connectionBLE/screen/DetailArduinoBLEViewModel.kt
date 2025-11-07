package com.wirtualworld.multiplatformiot.feature.connectionBLE.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.connectionBLE.ConnectToArduinoUseCase
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.connectionBLE.GetArduinoUseCase
import com.virtualworld.connectionBLE.ResponseState
import com.virtualworld.connectionBLE.SetChangerStateUseCase
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailArduinoBLEViewModel(
    private val getArduinoUseCase: GetArduinoUseCase,
    private val setChangerStateUseCase: SetChangerStateUseCase,
    private val connectToArduinoUseCase: ConnectToArduinoUseCase
) : ViewModel() {


    private val _arduinosState =
        MutableStateFlow<ArduinosState<ArduinoDomainModel>>(ArduinosState.Loading)
    val arduinosState: StateFlow<ArduinosState<ArduinoDomainModel>> = _arduinosState.asStateFlow()

    fun getArduino(arduinoAddress: String) {

        viewModelScope.launch {

            val arduinoConnect = connectToArduinoUseCase.connectToDevice(arduinoAddress)

            when (arduinoConnect) {
                is ResponseState.Error -> {
                    _arduinosState.value = ArduinosState.Error(arduinoConnect.exception)
                }

                is ResponseState.Success<Boolean> -> {

                    if (arduinoConnect.result) {

                        println("kkkkk+" + arduinoConnect.result)

                        _arduinosState.value = ArduinosState.Loading

                        getArduinoUseCase.getArduino().collect { responseArduinoDetail ->


                            when (responseArduinoDetail) {
                                is ResponseState.Error -> {
                                    _arduinosState.value = ArduinosState.Error(responseArduinoDetail.exception)
                                }

                                is ResponseState.Success<ArduinoDomainModel> -> {

                                    _arduinosState.value = ArduinosState.Success(responseArduinoDetail.result)


                                    println("vnm${_arduinosState.value}")
                                }
                            }

                        }
                    }
                }
            }
        }
    }


    fun updateState(numberState: String, arduinoAddress: String) {


        viewModelScope.launch {
            setChangerStateUseCase.changeState(numberState)
        }


    }

}