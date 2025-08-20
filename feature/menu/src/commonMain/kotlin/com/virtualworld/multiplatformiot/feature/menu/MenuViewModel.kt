package com.virtualworld.multiplatformiot.feature.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseState
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.GetArduinoActivateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class MenuViewModel(private val getArduinoActivateUseCase: GetArduinoActivateUseCase ) : ViewModel() {

    private val _arduinoActivesInternet = MutableStateFlow<StateScreenMenu<Map<String, Int>>>(StateScreenMenu.Loading)
    val arduinoActivesInternet: StateFlow<StateScreenMenu<Map<String, Int>>> = _arduinoActivesInternet


    fun getStatesArduinosInternet() {

        viewModelScope.launch {

            getArduinoActivateUseCase.getArduinoActivate("usuario1").collect{ response->

                when(response){
                    is ResponseState.Error -> {}
                    is ResponseState.Loading -> {StateScreenMenu.Loading}
                    is ResponseState.Success -> {
                        _arduinoActivesInternet.update {
                            StateScreenMenu.Success(response.result)
                        }
                    }
                }




            }

        }



    }


}