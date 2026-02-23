package com.virtualworld.multiplatformiot.domain.login.usecase

import com.virtualworld.multiplatformiot.domain.login.model.UserDomain
import com.virtualworld.multiplatformiot.domain.login.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class ObserveAuthStateUseCase(private val repository: AuthRepository) {

    operator fun invoke(): Flow<UserDomain?> = repository.authState()
}
