package com.virtualworld.multiplatformiot.data.connectionBluetooth

import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothDeviceDomain
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothRepository
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseState
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomain
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBCentralManagerScanOptionAllowDuplicatesKey
import platform.CoreBluetooth.CBManagerStatePoweredOff
import platform.CoreBluetooth.CBManagerStatePoweredOn
import platform.CoreBluetooth.CBManagerStateResetting
import platform.CoreBluetooth.CBManagerStateUnauthorized
import platform.CoreBluetooth.CBManagerStateUnsupported
import platform.CoreBluetooth.CBPeripheral
import platform.Foundation.NSNumber
import platform.darwin.NSObject
import platform.darwin.dispatch_get_main_queue

actual class ImplBluetoothRepository  : BluetoothRepository {

    // CoroutineScope para gestionar las operaciones en segundo plano.
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val centralManager: CBCentralManager by lazy {
        CBCentralManager(delegate = bluetoothDelegate, queue = dispatch_get_main_queue())
    }

    // Delegado que recibirá los eventos de CoreBluetooth (dispositivos encontrados, estado del Bluetooth, etc.).
    private val bluetoothDelegate = BluetoothDelegate()


    // --- IMPLEMENTACIÓN DE getPairedDevices ---

    actual override suspend fun getPairedDevices(): List<BluetoothDeviceDomain> {

        // Verifico que el Bluetooth esté encendido.
        if (centralManager.state != CBManagerStatePoweredOn) {
            println("[iOS] Bluetooth no está activado. Estado: ${centralManager.state}")
            // Podrías lanzar una excepción o devolver una lista vacía.
            return emptyList()
        }

        // Deferred que se completará cuando el escaneo termine.
        val scanCompletable = CompletableDeferred<List<BluetoothDeviceDomain>>()

        val discoveredDevices = mutableSetOf<BluetoothDeviceDomain>()

        //collect in ios
        // Asignamos una función al delegado para que nos notifique cuando encuentre un dispositivo.
        bluetoothDelegate.onPeripheralDiscovered = { peripheral ->
            val device = BluetoothDeviceDomain(
                name = peripheral.name() ?: "Dispositivo Desconocido",
                address = peripheral.identifier.UUIDString // La dirección en iOS es el UUID del periférico.
            )
            // Usamos un Set para evitar duplicados.
            discoveredDevices.add(device)
        }

        println("[iOS] Iniciando escaneo de dispositivos...")
        // Iniciamos el escaneo. nil significa buscar todos los dispositivos.
        centralManager.scanForPeripheralsWithServices(null, options = mapOf(CBCentralManagerScanOptionAllowDuplicatesKey to false))

        // Lanzamos una corrutina para detener el escaneo después de un tiempo.
        scope.launch {
            delay(5000) // Escanea durante 5 segundos.
            centralManager.stopScan()
            println("[iOS] Escaneo detenido. Encontrados ${discoveredDevices} dispositivos.")
            bluetoothDelegate.onPeripheralDiscovered = null // Limpiamos el callback.
            scanCompletable.complete(discoveredDevices.toList())
        }

        // Esperamos a que la corrutina de escaneo termine y devolvemos la lista.
        return scanCompletable.await()
    }

    actual override suspend fun connectToDevice(address: String): ResponseState<Boolean> {
        TODO("Not yet implemented")
    }


    actual override fun getAllStatesFlow(): Flow<ResponseState<ArduinoDomain>> {
        TODO("Not yet implemented")
    }

    actual override suspend fun sendStateCommand(stateNumber: String) {
        TODO("Not yet implemented")
    }

    actual override suspend fun disconnectFromDevice(address: String): Boolean {
        TODO("Not yet implemented")
    }

    actual override suspend fun isBluetoothEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    //Funcion delegada en ios
    private class BluetoothDelegate : NSObject(), CBCentralManagerDelegateProtocol {
        var onPeripheralDiscovered: ((CBPeripheral) -> Unit)? = null

        // Este méttodo es llamado cada vez que se descubre un periférico.
        override fun centralManager(central: CBCentralManager, didDiscoverPeripheral: CBPeripheral, advertisementData: Map<Any?, *>, RSSI: NSNumber) {
            println("Dispositivo encontrado: ${didDiscoverPeripheral.name() ?: "N/A"}, UUID: ${didDiscoverPeripheral.identifier.UUIDString}")
            onPeripheralDiscovered?.invoke(didDiscoverPeripheral)
        }

        // Este méttodo es llamado cuando el estado del Bluetooth cambia (encendido, apagado, etc.).
        override fun centralManagerDidUpdateState(central: CBCentralManager) {
            when (central.state) {
                CBManagerStatePoweredOn -> println("[iOS] Bluetooth está encendido.")
                CBManagerStatePoweredOff -> println("[iOS] Bluetooth está apagado.")
                CBManagerStateUnsupported -> println("[iOS] Bluetooth no es soportado en este dispositivo.")
                CBManagerStateUnauthorized -> println("[iOS] La app no está autorizada para usar Bluetooth.")
                CBManagerStateResetting -> println("[iOS] El servicio de Bluetooth se está reiniciando.")
                else -> println("[iOS] Estado de Bluetooth desconocido.")
            }
        }
    }
}