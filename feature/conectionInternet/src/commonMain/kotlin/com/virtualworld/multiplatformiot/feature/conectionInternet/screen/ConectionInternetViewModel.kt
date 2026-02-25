package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseStatesDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.GetListArduinosUseCase
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConectionInternetViewModel(private val getListArduinosUseCase: GetListArduinosUseCase) :
    ViewModel() {


    private val _arduinos =
        MutableStateFlow<ArduinosState<List<ArduinoDomainModel>>>(ArduinosState.Loading)
    val arduinosState: StateFlow<ArduinosState<List<ArduinoDomainModel>>> = _arduinos.asStateFlow()


    init {
        getAllArduinos()
    }

    private fun getAllArduinos() {

        _arduinos.update { ArduinosState.Loading }

        viewModelScope.launch {

            val listArduinos = getListArduinosUseCase()

            when (listArduinos) {
                is ResponseStatesDomain.Error -> _arduinos.update {
                    ArduinosState.Error(exception = listArduinos.exception)
                }
                is ResponseStatesDomain.Success -> _arduinos.update {
                    ArduinosState.Success(listArduinos.result)
                }
            }
        }
    }
}

