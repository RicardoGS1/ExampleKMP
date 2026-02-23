package com.virtualworld.multiplatformiot

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.initialize
import org.koin.dsl.module

class MainActivity : ComponentActivity() {

    private var pendingGoogleSignInCallback: ((String) -> Unit)? = null

    private lateinit var googleSignInClient: GoogleSignInClient

    private val requestBluetoothConnectPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("BluetoothPermission", "BLUETOOTH_CONNECT permission granted")
            } else {
                Log.w("BluetoothPermission", "BLUETOOTH_CONNECT permission denied")
            }
        }

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val callback = pendingGoogleSignInCallback
        pendingGoogleSignInCallback = null
        if (result.resultCode == RESULT_OK && data != null) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account?.idToken
                if (!idToken.isNullOrBlank()) {
                    callback?.invoke(idToken)
                } else {
                    Log.e(TAG, "Google Sign-In: idToken es null o vacío")
                }
            } catch (e: ApiException) {
                Log.e(TAG, "Google Sign-In falló: ${e.statusCode} - ${e.message}")
            }
        } else {
            Log.d(TAG, "Google Sign-In cancelado por el usuario")
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.initialize(this)

        val webClientId = getString(R.string.default_web_client_id)
        if (webClientId.isBlank()) {
            Log.e(TAG, "default_web_client_id no configurado en strings.xml")
        }
        googleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build()
        )

        checkAndRequestBluetoothConnectPermission()

        val onGoogleSignInRequest: ((String) -> Unit) -> Unit = { submitToken: (String) -> Unit ->
            pendingGoogleSignInCallback = submitToken
            googleSignInLauncher.launch(googleSignInClient.signInIntent)
        }

        setContent {
            enableEdgeToEdge()
            App(
                platformModule = module { single<android.content.Context> { applicationContext } },
                onGoogleSignInRequest = onGoogleSignInRequest
            )
        }
    }

    private fun checkAndRequestBluetoothConnectPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED -> {
                    Log.d("BluetoothPermission", "BLUETOOTH_CONNECT permission already granted")
                }
                shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH_CONNECT) -> {
                    Log.i("BluetoothPermission", "Showing rationale for BLUETOOTH_CONNECT permission")
                    requestBluetoothConnectPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
                }
                else -> {
                    requestBluetoothConnectPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
                }
            }
        } else {
            Log.d("BluetoothPermission", "BLUETOOTH_CONNECT not required for this API level")
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
