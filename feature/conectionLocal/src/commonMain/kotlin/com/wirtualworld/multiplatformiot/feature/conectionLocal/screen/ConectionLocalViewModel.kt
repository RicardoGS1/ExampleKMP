package com.wirtualworld.multiplatformiot.feature.conectionLocal.screen

import androidx.lifecycle.ViewModel
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.ui.core.models.ArduinosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class ConectionLocalViewModel : ViewModel() {

    private val _arduinos =
        MutableStateFlow<ArduinosState<List<ArduinoDomainModel>>>(ArduinosState.Error(Exception("Actualmente no esta desarrollado este modulo.")))
    val arduinosState: StateFlow<ArduinosState<List<ArduinoDomainModel>>> = _arduinos.asStateFlow()



}