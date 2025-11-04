package com.virtualworld.connectionBLE

data class BLEDeviceDomain(
    val name: String,
    val address: String,
    val isConnected: Boolean = false
) 