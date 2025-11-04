package com.wirtualworld.multiplatformiot.feature.connectionBluetooth.screen.bluetoothClassic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothDeviceDomain
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.GetPairedDevicesUseCase
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseState
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConnectionBluetoothViewModel(
    private val getPairedDevicesUseCase: GetPairedDevicesUseCase
) : ViewModel() {

    private val _arduinos =
        MutableStateFlow<ArduinosState<List<BluetoothDeviceDomain>>>(ArduinosState.Loading)
    val arduinosState: StateFlow<ArduinosState<List<BluetoothDeviceDomain>>> =
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

                is ResponseState.Success<List<BluetoothDeviceDomain>> -> {
                    _arduinos.value = ArduinosState.Success(devices.result)
                }
            }

        }
    }

}