package com.wirtualworld.multiplatformiot.feature.connectionBLE.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.connectionBLE.BLEDeviceDomain
import com.virtualworld.connectionBLE.GetPairedDevicesUseCase
import com.virtualworld.connectionBLE.ResponseState
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConnectionBLEViewModel (
    private val getPairedDevicesUseCase: GetPairedDevicesUseCase
    ) : ViewModel() {

        private val _arduinos =
            MutableStateFlow<ArduinosState<List<BLEDeviceDomain>>>(ArduinosState.Loading)
        val arduinosState: StateFlow<ArduinosState<List<BLEDeviceDomain>>> =
            _arduinos.asStateFlow()

        init {
            loadPairedDevices()
        }

        fun loadPairedDevices() {
            viewModelScope.launch {

                val devices = getPairedDevicesUseCase()

                when (devices) {
                    is ResponseState.Error -> {
                        _arduinos.value = ArduinosState.Error(devices.exception)
                    }

                    is ResponseState.Success<List<BLEDeviceDomain>> -> {
                        _arduinos.value = ArduinosState.Success(devices.result)
                    }
                }

            }
        }

    }