package com.virtualworld.multiplatformiot.domain.connectionBluetooth

data class BluetoothDeviceDomain(
    val name: String,
    val address: String,
    val isConnected: Boolean = false
) 