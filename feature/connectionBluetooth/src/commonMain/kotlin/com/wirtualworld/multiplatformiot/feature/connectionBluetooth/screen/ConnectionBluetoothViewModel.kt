package com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.GetPairedDevicesUseCase
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseStateDomain
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConnectionBluetoothViewModel(
    private val getPairedDevicesUseCase: GetPairedDevicesUseCase
) : ViewModel() {

    private val _arduinos =
        MutableStateFlow<ArduinosState<List<ArduinoDomainModel>>>(ArduinosState.Loading)
    val arduinosState: StateFlow<ArduinosState<List<ArduinoDomainModel>>> =
        _arduinos.asStateFlow()

    init {
        loadPairedDevices()
    }

    fun loadPairedDevices() {
        viewModelScope.launch {

            val devices = getPairedDevicesUseCase()

            when (devices) {
                is ResponseStateDomain.Error -> {
                    _arduinos.value = ArduinosState.Error(devices.exception)
                }

                is ResponseStateDomain.Success<List<ArduinoDomainModel>> -> {
                    _arduinos.value = ArduinosState.Success(devices.result)
                }
            }

        }
    }

}