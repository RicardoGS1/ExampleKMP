package com.virtualworld.multiplatformiot.feature.conectionInternet.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.conectionInternet.usecase.AddArduinoUseCase
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

const val MAX_STATE_OBJECTS = 5

class AddArduinoViewModel(private val addArduinoUseCase: AddArduinoUseCase) : ViewModel() {

    var name = MutableStateFlow("")
    var stateObjects = MutableStateFlow<Map<String, Boolean>>(emptyMap())

    private val _saveState = MutableStateFlow<ArduinosState<Unit>>(ArduinosState.Loading)
    val saveState: StateFlow<ArduinosState<Unit>> = _saveState.asStateFlow()

    fun onNameChange(newName: String) {
        name.value = newName
    }

    fun addStateObject(key: String, value: Boolean) {
        if (key.isBlank()) return
        if (stateObjects.value.size >= MAX_STATE_OBJECTS) return
        stateObjects.value = stateObjects.value.toMutableMap().apply { put(key.trim(), value) }
    }

    fun updateStateObjectValue(key: String, value: Boolean) {
        if (key !in stateObjects.value) return
        stateObjects.value = stateObjects.value.toMutableMap().apply { put(key, value) }
    }

    fun removeStateObject(key: String) {
        stateObjects.value = stateObjects.value.toMutableMap().apply { remove(key) }
    }

    fun canAddMoreStateObjects(): Boolean = stateObjects.value.size < MAX_STATE_OBJECTS

    fun saveArduino() {
        viewModelScope.launch {
            _saveState.value = ArduinosState.Loading
            try {
                addArduinoUseCase.addArduino(
                    name = name.value.trim(),
                    states = stateObjects.value
                )
                _saveState.value = ArduinosState.Success(Unit)
            } catch (e: Exception) {
                _saveState.value = ArduinosState.Error(e)
            }
        }
    }
}
