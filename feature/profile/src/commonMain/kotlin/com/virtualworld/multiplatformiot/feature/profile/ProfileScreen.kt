package com.virtualworld.multiplatformiot.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.domain.login.model.UserDomain
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.ButtonBack

@Composable
internal fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateBack: () -> Unit,
) {
    val user by viewModel.user.collectAsState()
    val signOutState by viewModel.signOutState.collectAsState()

    LaunchedEffect(signOutState) {
        if (signOutState is ProfileViewModel.SignOutState.Success) {
            // El AuthStateViewModel emitirá null y MainScreen navegará al login
            // No hace falta navegar aquí
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(top = 32.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(MyAppTheme.padding.large)) {
            ButtonBack(onClick = onNavigateBack)
            Spacer(Modifier.height(24.dp))

            Text(
                text = "Mi perfil",
                style = MyAppTheme.typography.titleLarge,
                color = MyAppTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 12.dp)
            )
            Spacer(Modifier.height(24.dp))

            when {
                user != null -> UserContent(
                    user = user!!,
                    signOutState = signOutState,
                    onSignOut = viewModel::signOut,
                )
                else -> Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No hay datos de usuario",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MyAppTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun UserContent(
    user: UserDomain,
    signOutState: ProfileViewModel.SignOutState,
    onSignOut: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MyAppTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MyAppTheme.padding.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MyAppTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MyAppTheme.colorScheme.onPrimaryContainer
                )
            }

            Text(
                text = user.displayName?.ifBlank { "Usuario" } ?: "Usuario",
                style = MyAppTheme.typography.labelLarge ,
                color = MyAppTheme.colorScheme.onSurface
            )
            val userEmail = user.email
            if (!userEmail.isNullOrBlank()) {
                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.bodyMedium ,
                    color = MyAppTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "UID: ${user.uid.take(12)}...",
                style = MaterialTheme.typography.bodySmall,
                color = MyAppTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            when (signOutState) {
                is ProfileViewModel.SignOutState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
                is ProfileViewModel.SignOutState.Error -> {
                    Text(
                        text = (signOutState as ProfileViewModel.SignOutState.Error).message,
                        style = MaterialTheme.typography.bodySmall,
                        color = MyAppTheme.colorScheme.secondary
                    )
                    OutlinedButton(onClick = onSignOut) {
                        Text("Cerrar sesión")
                    }
                }
                else -> {
                    Button(
                        onClick = onSignOut,
                        enabled = signOutState !is ProfileViewModel.SignOutState.Loading
                    ) {
                        Text("Cerrar sesión")
                    }
                }
            }
        }
    }
}
