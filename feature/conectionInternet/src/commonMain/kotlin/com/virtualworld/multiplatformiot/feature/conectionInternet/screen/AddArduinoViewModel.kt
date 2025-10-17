package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.AddArduinoUseCase
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddArduinoViewModel(private val addArduinoUseCase: AddArduinoUseCase) : ViewModel() {

    var name = MutableStateFlow("")
    var state1 = MutableStateFlow(mapOf<String, Boolean>())

    private val _saveState = MutableStateFlow<ArduinosState<Unit>>(ArduinosState.Loading)
    val saveState: StateFlow<ArduinosState<Unit>> = _saveState.asStateFlow()

    fun onNameChange(newName: String) {
        name.value = newName
    }

    fun onState1Change(key: String, value: Boolean) {
        state1.value = state1.value.toMutableMap().apply { put(key, value) }
    }

    fun saveArduino() {
        viewModelScope.launch {
            try {
                addArduinoUseCase.addArduino(
                    name = name.value,
                    states = state1.value
                )
                _saveState.value = ArduinosState.Success(Unit)
            } catch (e: Exception) {
                _saveState.value = ArduinosState.Error(e)
            }
        }
    }
} 