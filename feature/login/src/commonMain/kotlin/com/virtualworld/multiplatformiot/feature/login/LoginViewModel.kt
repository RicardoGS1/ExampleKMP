package com.virtualworld.multiplatformiot.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.login.model.AuthResult
import com.virtualworld.multiplatformiot.domain.login.usecase.SignInWithEmailUseCase
import com.virtualworld.multiplatformiot.domain.login.usecase.SignInWithGoogleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email, errorMessage = null) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password, errorMessage = null) }
    }

    fun signInWithEmail() {
        val email = _state.value.email
        val password = _state.value.password
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = signInWithEmailUseCase(email, password)) {
                is AuthResult.Success -> {
                    _state.update { it.copy(isLoading = false, errorMessage = null) }
                    // Navegación se maneja en la UI al detectar usuario
                }
                is AuthResult.Error -> {
                    _state.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                is AuthResult.Loading -> { }
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = signInWithGoogleUseCase(idToken)) {
                is AuthResult.Success -> {
                    _state.update { it.copy(isLoading = false, errorMessage = null) }
                }
                is AuthResult.Error -> {
                    _state.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                is AuthResult.Loading -> { }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
