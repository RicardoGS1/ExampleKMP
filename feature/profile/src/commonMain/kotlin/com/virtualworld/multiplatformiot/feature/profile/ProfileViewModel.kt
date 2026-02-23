package com.virtualworld.multiplatformiot.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.multiplatformiot.domain.login.model.AuthResult
import com.virtualworld.multiplatformiot.domain.login.model.UserDomain
import com.virtualworld.multiplatformiot.domain.login.usecase.GetCurrentUserUseCase
import com.virtualworld.multiplatformiot.domain.login.usecase.SignOutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {

    private val _user = MutableStateFlow<UserDomain?>(null)
    val user: StateFlow<UserDomain?> = _user.asStateFlow()

    private val _signOutState = MutableStateFlow<SignOutState>(SignOutState.Idle)
    val signOutState: StateFlow<SignOutState> = _signOutState.asStateFlow()

    init {
        _user.value = getCurrentUserUseCase()
    }

    fun signOut() {
        viewModelScope.launch {
            _signOutState.value = SignOutState.Loading
            when (val result = signOutUseCase()) {
                is AuthResult.Success -> _signOutState.value = SignOutState.Success
                is AuthResult.Error -> _signOutState.value = SignOutState.Error(result.message)
                AuthResult.Loading -> { /* ya mostramos Loading */ }
            }
        }
    }

    sealed interface SignOutState {
        data object Idle : SignOutState
        data object Loading : SignOutState
        data object Success : SignOutState
        data class Error(val message: String) : SignOutState
    }
}
