package com.virtualworld.multiplatformiot.data.login

import com.virtualworld.multiplatformiot.domain.login.model.AuthResult
import com.virtualworld.multiplatformiot.domain.login.model.UserDomain
import com.virtualworld.multiplatformiot.domain.login.repository.AuthRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override val currentUser: UserDomain?
        get() = firebaseAuth.currentUser?.toUserDomain()

    override fun authState(): Flow<UserDomain?> =
        firebaseAuth.authStateChanged.map { it?.toUserDomain() }

    override suspend fun signInWithEmail(email: String, password: String): AuthResult<UserDomain> =
        runCatchAuth {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password)
            AuthResult.Success(result.user!!.toUserDomain())
        }

    override suspend fun signUpWithEmail(email: String, password: String): AuthResult<UserDomain> =
        runCatchAuth {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password)
            AuthResult.Success(result.user!!.toUserDomain())
        }

    override suspend fun signInWithGoogle(idToken: String): AuthResult<UserDomain> =
        runCatchAuth {
            val credential = GoogleAuthProvider.credential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential)
            AuthResult.Success(result.user!!.toUserDomain())
        }

    override suspend fun signOut(): AuthResult<Unit> =
        runCatchAuth {
            firebaseAuth.signOut()
            AuthResult.Success(Unit)
        }

    private inline fun <T> runCatchAuth(block: () -> AuthResult<T>): AuthResult<T> {
        return try {
            block()
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Error de autenticación")
        }
    }
}

private fun dev.gitlive.firebase.auth.FirebaseUser.toUserDomain(): UserDomain =
    UserDomain(
        uid = uid,
        email = email,
        displayName = displayName,
        photoUrl = photoURL
    )
