package com.virtualworld.multiplatformiot

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.initialize
import org.koin.dsl.module

class MainActivity : ComponentActivity() {

    private val requestBluetoothConnectPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, you can now perform Bluetooth operations
                // that require BLUETOOTH_CONNECT
                Log.d("BluetoothPermission", "BLUETOOTH_CONNECT permission granted")
                // Initialize your Bluetooth related functionality here
            } else {
                // Permission denied. Handle this gracefully.
                // You might want to explain to the user why the permission is needed
                // or disable Bluetooth functionality.
                Log.w("BluetoothPermission", "BLUETOOTH_CONNECT permission denied")
                // Show a message to the user or disable features
            }
        }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.initialize(this)

        checkAndRequestBluetoothConnectPermission()


        setContent {

            enableEdgeToEdge()

            App(platformModule = module { single<Context> { applicationContext } })
        }
    }

    private fun checkAndRequestBluetoothConnectPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // BLUETOOTH_CONNECT is for Android 12+
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission is already granted
                    Log.d("BluetoothPermission", "BLUETOOTH_CONNECT permission already granted")
                    // Initialize your Bluetooth related functionality here
                }
                shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH_CONNECT) -> {
                    // Explain to the user why you need this permission.
                    // Then, request the permission.
                    Log.i("BluetoothPermission", "Showing rationale for BLUETOOTH_CONNECT permission")
                    // You could show a dialog here explaining the need for the permission
                    requestBluetoothConnectPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
                }
                else -> {
                    // Directly request the permission
                    requestBluetoothConnectPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
                }
            }
        } else {
            // For older Android versions (below Android 12),
            // BLUETOOTH and BLUETOOTH_ADMIN permissions declared in the manifest are sufficient
            // and are granted at install time.
            // However, the error specifically mentions BLUETOOTH_CONNECT,
            // so this 'else' branch might not be strictly necessary for THIS error,
            // but it's good practice for handling Bluetooth permissions across API levels.
            Log.d("BluetoothPermission", "BLUETOOTH_CONNECT not required for this API level or already handled by older permissions.")
            // Initialize your Bluetooth related functionality here
        }
    }
}

