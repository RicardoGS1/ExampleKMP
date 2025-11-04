package com.virtualworld.multiplatformiot.domain.connectionBluetooth

data class BLEDeviceDomain(
    val name: String,
    val address: String,
    val isConnected: Boolean = false
) 