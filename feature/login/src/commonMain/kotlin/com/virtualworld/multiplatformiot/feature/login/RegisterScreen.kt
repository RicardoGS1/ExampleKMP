package com.virtualworld.multiplatformiot.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.virtualworld.multiplatformiot.ui.core.MyAppTheme
import com.virtualworld.multiplatformiot.ui.core.component.DEFAULT_RECT_HEIGHT_MENU
import multiplatformiot.ui.core.generated.resources.Res
import multiplatformiot.ui.core.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun RegisterScreen(
    viewModel: RegisterViewModel,
    onNavigateToLogin: () -> Unit,
    onAuthSuccess: () -> Unit,
    showGoogleSignIn: Boolean = true,
    onGoogleSignInRequest: ((String) -> Unit) -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    val primaryColor = MyAppTheme.colorScheme.primary
    val primaryContainerColor = MyAppTheme.colorScheme.primaryContainer

    // Degradado que funciona tanto en modo claro como oscuro
    val brush = remember(primaryColor, primaryContainerColor) {
        Brush.horizontalGradient(
            colors = listOf(primaryContainerColor, primaryColor),
            startX = -1000f,
        )
    }

    LaunchedEffect(Unit) {
        viewModel.clearError()
    }

    Text(
        modifier =  Modifier.padding(top = MyAppTheme.padding.barNotifications + 32.dp, start = 32.dp),
        text = "Crear cuenta",
        style = MyAppTheme.typography.titleLarge,
        color = MyAppTheme.colorScheme.onSurface,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = DEFAULT_RECT_HEIGHT_MENU.dp-64.dp)
            .padding(horizontal = 32.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {


        Box(
            modifier = Modifier
                .size(128.dp)
                .border(
                    width = 12.dp,
                    color = MyAppTheme.colorScheme.background,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            // Usamos un Box interno para el fondo con degradado (brush)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp) // El mismo grosor que el borde para que encaje perfecto
                    .clip(CircleShape)
                    .background(brush),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Icono de la App",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Correo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Contraseña (mín. 6 caracteres)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            label = { Text("Repetir contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        state.errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = msg,
                color = MyAppTheme.colorScheme.onPrimary,
                style = MyAppTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                focusManager.clearFocus()
                viewModel.signUpWithEmail()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading,
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.height(24.dp).padding(end = 8.dp),
                    color = MyAppTheme.colorScheme.onPrimary,
                )
            }
            Text(if (state.isLoading) "Creando cuenta…" else "Registrarse")
        }

        if (showGoogleSignIn) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    focusManager.clearFocus()
                    onGoogleSignInRequest { token -> viewModel.signInWithGoogle(token) }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
            ) {
                Text("Continuar con Google")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}
