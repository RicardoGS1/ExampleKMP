package com.virtualworld.multiplatformiot.data.connectionBLE

import com.virtualworld.connectionBLE.BLEDeviceDomain
import com.virtualworld.connectionBLE.BLERepository
import com.virtualworld.connectionBLE.ResponseState
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.domain.core.models.StateObjectDomain
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBCentralManagerScanOptionAllowDuplicatesKey
import platform.CoreBluetooth.CBCharacteristic
import platform.CoreBluetooth.CBCharacteristicPropertyWrite
import platform.CoreBluetooth.CBCharacteristicPropertyWriteWithoutResponse
import platform.CoreBluetooth.CBCharacteristicWriteWithResponse
import platform.CoreBluetooth.CBCharacteristicWriteWithoutResponse
import platform.CoreBluetooth.CBManagerStatePoweredOff
import platform.CoreBluetooth.CBManagerStatePoweredOn
import platform.CoreBluetooth.CBManagerStateResetting
import platform.CoreBluetooth.CBManagerStateUnauthorized
import platform.CoreBluetooth.CBManagerStateUnsupported
import platform.CoreBluetooth.CBPeripheral
import platform.CoreBluetooth.CBPeripheralDelegateProtocol
import platform.CoreBluetooth.CBService
import platform.CoreBluetooth.CBUUID
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSNumber
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.darwin.NSObject
import platform.darwin.dispatch_get_main_queue


actual class ImplBLERepository : BLERepository {

    // CoroutineScope para gestionar las operaciones en segundo plano.
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val centralManager: CBCentralManager by lazy {
        CBCentralManager(delegate = bluetoothDelegate, queue = dispatch_get_main_queue())
    }

    // Delegado que recibirá los eventos de CoreBluetooth (dispositivos encontrados, estado del Bluetooth, etc.).
    private val bluetoothDelegate = BluetoothDelegate()


    // Almacena una referencia al objeto CBPeripheral para poder conectarse más tarde
    private val discoveredPeripherals = mutableMapOf<String, CBPeripheral>()

    // Y una referencia al periférico conectado actualmente
    private var connectedPeripheral: CBPeripheral? = null


    private val serviceUUID = CBUUID.UUIDWithString("0000181C-1234-1000-8000-00805F9B34FB")

    // Propiedad para guardar la referencia a la característica donde escribiremos los comandos
    private var writeCharacteristic: List<CBCharacteristic>? = null

    // Delegado para manejar eventos del periférico conectado (descubrimiento de servicios/características)
    private val peripheralDelegate = PeripheralDelegate()

    private var dataArduinoActual: ArduinoDomainModel = ArduinoDomainModel()


    // --- IMPLEMENTACIÓN DE getPairedDevices ---

    actual override suspend fun getPairedDevices(): List<ArduinoDomainModel> {

        // Verifico que el Bluetooth esté encendido.
        if (centralManager.state != CBManagerStatePoweredOn) {
            println("[iOS] Bluetooth no está activado. Estado: ${centralManager.state}")
            // Podrías lanzar una excepción o devolver una lista vacía.
            return emptyList()
        }

        // Deferred que se completará cuando el escaneo termine.
        val scanCompletable = CompletableDeferred<List<ArduinoDomainModel>>()

        val discoveredDevices = mutableSetOf<ArduinoDomainModel>()

        //collect in ios
        // Asignamos una función al delegado para que nos notifique cuando encuentre un dispositivo.
        bluetoothDelegate.onPeripheralDiscovered = { peripheral ->
            val device = ArduinoDomainModel(
                name = peripheral.name ?: "Dispositivo Desconocido",
                address = peripheral.identifier.UUIDString // La dirección en iOS es el UUID del periférico.
            )
            // Usamos un Set para evitar duplicados.
            if (discoveredDevices.add(device)) {
                // Guardo el objeto CBPeripheral real usando su UUID como clave.
                discoveredPeripherals[device.address] = peripheral
            }
        }

        println("[iOS] Iniciando escaneo de dispositivos...")
        // Iniciamos el escaneo. nil significa buscar todos los dispositivos.
        centralManager.scanForPeripheralsWithServices(
            null,
            options = mapOf(CBCentralManagerScanOptionAllowDuplicatesKey to false)
        )

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

        // 1. Buscamos en nuestro mapa el periférico que corresponde a la dirección (UUID)
        val peripheralToConnect = discoveredPeripherals[address]
            ?: return ResponseState.Error(Exception("Dispositivo no encontrado o fuera de alcance. Por favor, escanee de nuevo."))

        // Si ya estamos conectados a ese dispositivo, devolvemos éxito.
        if (peripheralToConnect.state == platform.CoreBluetooth.CBPeripheralStateConnected) {
            println("[iOS] Ya conectado a ${peripheralToConnect.name()}.")
            return ResponseState.Success(true)
        }

        println("[iOS] Intentando conectar a: ${peripheralToConnect.name()}")

        // 2. Creamos un Deferred que se completará desde el delegado.
        val connectionCompletable = CompletableDeferred<ResponseState<Boolean>>()

        // 3. Asignamos los callbacks al delegado ANTES de intentar conectar.
        bluetoothDelegate.onConnectionResult = { success, error ->
            if (success) {
                connectedPeripheral =
                    peripheralToConnect // Guardamos la referencia al periférico conectado
                connectionCompletable.complete(ResponseState.Success(true))
            } else {
                connectionCompletable.complete(
                    ResponseState.Error(
                        Exception(
                            error ?: "Fallo al conectar."
                        )
                    )
                )
            }
        }

        // 4. Iniciamos la conexión. El resultado llegará al delegado.
        centralManager.connectPeripheral(peripheralToConnect, null)

        // 5. Esperamos a que el delegado complete el Deferred.
        return connectionCompletable.await()
    }


    actual override fun getAllStatesFlow(): Flow<ResponseState<ArduinoDomainModel>> = flow {

        // 1. Verificamos que estamos conectados a un periférico
        val peripheral = connectedPeripheral ?: run {
            emit(ResponseState.Error(Exception("No hay ningún periférico conectado.")))
            // close()
            return@flow
        }

        // 2. Asignamos el delegado al periférico
        peripheral.delegate = peripheralDelegate


        try {
            // 3. Descubrir el servicio ESPECÍFICO usando su UUID
            peripheralDelegate.servicesCompletable = CompletableDeferred()
            // Pasamos una lista que contiene solo el UUID de nuestro servicio
            peripheral.discoverServices(listOf(serviceUUID))
            println("[iOS] Buscando el servicio específico: ${serviceUUID.UUIDString}")

            val services = withTimeout(5000) { peripheralDelegate.servicesCompletable.await() }

            // 4. Encontrar el servicio y sus características
            val myService =
                services.firstOrNull() // Tomamos el primer (y único) servicio encontrado
                    ?: throw Exception("Servicio con UUID ${serviceUUID.UUIDString} no encontrado.")

            println("[iOS] Servicio encontrado. Buscando sus características...")
            peripheralDelegate.characteristicsCompletable = CompletableDeferred()
            peripheral.discoverCharacteristics(
                null,
                forService = myService
            ) // null = buscar todas las características de ESTE servicio

            val characteristics =
                withTimeout(5000) { peripheralDelegate.characteristicsCompletable.await() }

            // 5. Filtrar solo las características que se pueden leer
            val readableChars = characteristics.filter {
                (it.properties and platform.CoreBluetooth.CBCharacteristicPropertyRead) != 0uL
            }
            println("[iOS] Características legibles encontradas: ${readableChars.count()}")

            val allStates = mutableMapOf<String, StateObjectDomain>()

            while (true) {
                // 6. Leer el valor de cada característica legible
                for (charToRead in readableChars) {

                    peripheralDelegate.characteristicReadCompletable = CompletableDeferred()
                    peripheral.readValueForCharacteristic(charToRead)

                    val rawValue =
                        withTimeout(2000) { peripheralDelegate.characteristicReadCompletable.await() }

                    if (rawValue != null) {
                        val stringValue =
                            NSString.create(data = rawValue, encoding = NSUTF8StringEncoding)
                        println("[iOS] Leído valor de ${charToRead.UUID()}: $stringValue")

                        val stateNumber =
                            searchNumberCharacteristic(charToRead.UUID().toString())

                        val state = StateObjectDomain(
                            nombre = stateNumber,
                            estado = stringValue?.equals("1")
                                ?: false // Simplificado: es true solo si el string es "1"
                        )
                        allStates[stateNumber] = state
                    } else {
                        println("[iOS] No se pudo leer el valor para la característica ${charToRead.UUID()}")
                    }
                }


                // 7. Emitir el resultado final
                dataArduinoActual =
                    ArduinoDomainModel(
                        name = peripheral.name() ?: "Dispositivo",
                        active = !dataArduinoActual.active,
                        state1 = allStates
                    )

                emit(ResponseState.Success( dataArduinoActual))
                delay(2000)
            }

        } catch (e: Exception) {
            println("[iOS] Error en getAllStatesFlow: ${e.message}")
            emit(ResponseState.Error(e))
        }
    }


    actual override suspend fun sendStateCommand(stateNumber: String) {
        // 1. Verificamos que estamos conectados a un periférico
        val peripheral = connectedPeripheral ?: run {
            println("[iOS] Error: No hay ningún periférico conectado.")
            return
        }
//
        // 2. Asignamos el delegado al periférico si no lo hemos hecho ya
        if (peripheral.delegate != peripheralDelegate) {
            peripheral.delegate = peripheralDelegate
        }
//
        try {

            // 3. Descubrir el servicio ESPECÍFICO usando su UUID
            peripheralDelegate.servicesCompletable = CompletableDeferred()
            // Pasamos una lista que contiene solo el UUID de nuestro servicio
            peripheral.discoverServices(listOf(serviceUUID))
            println("[iOS] Buscando el servicio específico: ${serviceUUID.UUIDString}")


            val services = withTimeout(5000) { peripheralDelegate.servicesCompletable.await() }

            // 4. Encontrar el servicio y sus características
            val myService =
                services.firstOrNull() // Tomamos el primer (y único) servicio encontrado
                    ?: throw Exception("Servicio con UUID ${serviceUUID.UUIDString} no encontrado.")

            println("[iOS] Servicio encontrado. Buscando sus características...")
            peripheralDelegate.characteristicsCompletable = CompletableDeferred()
            peripheral.discoverCharacteristics(
                null,
                forService = myService
            ) // null = buscar todas las características de ESTE servicio

            val characteristics =
                withTimeout(5000) { peripheralDelegate.characteristicsCompletable.await() }

            println("[iOS] caracteristicas encontradas")

            val stateCode = searchCodecCharacteristic(stateNumber)

            // 3c. Encontrar y guardar la característica que permite escritura
            val characteristicWriteFind = characteristics.firstOrNull {

                it.UUID.UUIDString() == stateCode &&
                        ((it.properties and CBCharacteristicPropertyWrite) != 0uL ||
                                (it.properties and CBCharacteristicPropertyWriteWithoutResponse) != 0uL)

            }

            println("[iOS] caracteristica espesifica guardada ${characteristicWriteFind?.UUID}")


            if (characteristicWriteFind == null) {
                throw Exception("No se encontró ninguna característica con permiso de escritura.")
            }


            val stateActual = dataArduinoActual.state1?.entries?.firstOrNull() {
                println("[iOS] estado a buscar $stateNumber")
                it.value.nombre == stateNumber
            }

            println("[iOS] estado actial de caracteristica $stateActual")

            var writeData = "0"

            if (stateActual!!.value.estado == true) {
                writeData = "0"
            } else
                writeData = "1"


            // 4. Convertimos el String a datos (NSData)
            val dataToSend: NSData = (writeData as NSString).dataUsingEncoding(NSUTF8StringEncoding)
                ?: throw Exception("No se pudo convertir el string a datos UTF-8.")

            // 5. Escribimos los datos en la característica
            // Elige el tipo de escritura según lo que configuraste en Bluetooth LE Explorer
            val writeType =
                if (characteristicWriteFind.properties and (CBCharacteristicPropertyWriteWithoutResponse) != 0uL) {
                    CBCharacteristicWriteWithoutResponse
                } else {
                    CBCharacteristicWriteWithResponse
                }

            peripheral.writeValue(
                dataToSend,
                forCharacteristic = characteristicWriteFind,
                type = writeType
            )
            println("[iOS] Comando '$stateNumber' enviado exitosamente.")

        } catch (e: Exception) {
            println("[iOS] Error al enviar comando: ${e.message}")
            // Si hay un error (ej: desconexión), limpiamos la característica para que la busque de nuevo
            writeCharacteristic = null
        }
    }


    actual override suspend fun disconnectFromDevice(address: String): Boolean {
        TODO("Not yet implemented")
    }

    actual override suspend fun isBluetoothEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    private fun searchNumberCharacteristic(uuidCharacteristic: String): String {

        val states = mapOf<String, String>(
            "state1" to "00002BEC-0011-1000-8000-00805F9B34FB",
            "state2" to "00002BEC-0012-1000-8000-00805F9B34FB",
            "state3" to "00002BEC-0013-1000-8000-00805F9B34FB",
            "state4" to "00002BEC-0014-1000-8000-00805F9B34FB",
            "state5" to "00002BEC-0015-1000-8000-00805F9B34FB"
        )

        // Busca la primera "entrada" (par clave-valor) donde el valor coincida con el UUID.
        // Si la encuentra, devuelve su clave. Si no, devuelve un string vacío.
        return states.entries
            .find { it.value.equals(uuidCharacteristic, ignoreCase = true) }
            ?.key
            ?: ""
    }

    private fun searchCodecCharacteristic(stateNumber: String): String {

        val states = mapOf<String, String>(
            "state1" to "00002BEC-0011-1000-8000-00805F9B34FB",
            "state2" to "00002BEC-0012-1000-8000-00805F9B34FB",
            "state3" to "00002BEC-0013-1000-8000-00805F9B34FB",
            "state4" to "00002BEC-0014-1000-8000-00805F9B34FB",
            "state5" to "00002BEC-0015-1000-8000-00805F9B34FB"
        )


        return states.entries
            .find { it.key.equals(stateNumber, ignoreCase = true) }
            ?.value
            ?: ""
    }


    private class BluetoothDelegate : NSObject(), CBCentralManagerDelegateProtocol {

        var onPeripheralDiscovered: ((CBPeripheral) -> Unit)? = null
        var onConnectionResult: ((success: Boolean, error: String?) -> Unit)? = null

        override fun centralManager(
            central: CBCentralManager,
            didDiscoverPeripheral: CBPeripheral,
            advertisementData: Map<Any?, *>,
            RSSI: NSNumber
        ) {
            val peripheralName = didDiscoverPeripheral.name
            println(peripheralName)

//            if (peripheralName == null || peripheralName.isBlank()) {
//                return
//            }
            onPeripheralDiscovered?.invoke(didDiscoverPeripheral)
        }

        override fun centralManagerDidUpdateState(central: CBCentralManager) {
            // NOTA: Para que esto funcione, quita las importaciones explícitas de cada estado
            // (CBManagerStatePoweredOn, etc.) de la parte superior del archivo.
            when (central.state) {
                CBManagerStatePoweredOn -> println("[iOS] Bluetooth está encendido.")
                CBManagerStatePoweredOff -> println("[iOS] Bluetooth está apagado.")
                CBManagerStateUnsupported -> println("[iOS] Bluetooth no es soportado en este dispositivo.")
                CBManagerStateUnauthorized -> println("[iOS] La app no está autorizada para usar Bluetooth.")
                CBManagerStateResetting -> println("[iOS] El servicio de Bluetooth se está reiniciando.")
            }
        }

        // --- MÉTODOS CORREGIDOS ---

        override fun centralManager(central: CBCentralManager, didConnectPeripheral: CBPeripheral) {
            println("[iOS] Conectado exitosamente a: ${didConnectPeripheral.name}")
            onConnectionResult?.invoke(true, null)
            onConnectionResult = null
        }

        // FIRMA CORREGIDA 1
        override fun centralManager(
            central: CBCentralManager,
            didFailToConnectPeripheral: CBPeripheral,
            error: NSError?
        ) {
            val errorMessage = error?.localizedDescription ?: "Error desconocido al conectar"
            println("[iOS] Fallo al conectar a: ${didFailToConnectPeripheral.name}, Error: $errorMessage")
            onConnectionResult?.invoke(false, errorMessage)
            onConnectionResult = null
        }

//        // FIRMA CORREGIDA 2 (diferente de la anterior)
//        override fun centralManager(central: CBCentralManager, didDisconnectPeripheral: CBPeripheral, error: NSError?,) {
//            val errorMessage = if (error != null) " con error: ${error.localizedDescription}" else ""
//            println("[iOS] Desconectado de: ${didDisconnectPeripheral.name()}$errorMessage")
//            // Aquí puedes actualizar la UI o intentar reconectar.
//            connectedPeripheral = null
//        }
    }

    // Al final de tu archivo
    private class PeripheralDelegate : NSObject(), CBPeripheralDelegateProtocol {
        // Deferred para esperar el descubrimiento de servicios
        var servicesCompletable = CompletableDeferred<List<CBService>>()

        // Deferred para esperar el descubrimiento de características
        var characteristicsCompletable = CompletableDeferred<List<CBCharacteristic>>()

        // ---> NOVEDAD: Deferred para esperar el valor de una característica leída <---
        var characteristicReadCompletable = CompletableDeferred<NSData?>()

        override fun peripheral(peripheral: CBPeripheral, didDiscoverServices: NSError?) {
            if (didDiscoverServices != null) {
                servicesCompletable.completeExceptionally(Exception("Error descubriendo servicios: ${didDiscoverServices.localizedDescription}"))
                return
            }
            servicesCompletable.complete(peripheral.services as? List<CBService> ?: emptyList())
        }

        override fun peripheral(
            peripheral: CBPeripheral,
            didDiscoverCharacteristicsForService: CBService,
            error: NSError?
        ) {
            if (error != null) {
                characteristicsCompletable.completeExceptionally(Exception("Error descubriendo características: ${error.localizedDescription}"))
                return
            }
            characteristicsCompletable.complete(
                didDiscoverCharacteristicsForService.characteristics as? List<CBCharacteristic>
                    ?: emptyList()
            )
        }

        // Métkodo que se llama cuando se recibe el valor de una característica <---
        override fun peripheral(
            peripheral: CBPeripheral,
            didUpdateValueForCharacteristic: CBCharacteristic,
            error: NSError?
        ) {
            if (error != null) {
                println("[iOS] Error al leer la característica ${didUpdateValueForCharacteristic.UUID}: ${error.localizedDescription}")
                characteristicReadCompletable.complete(null)
                return
            }
            // Completamos el Deferred con el valor recibido (NSData)
            characteristicReadCompletable.complete(didUpdateValueForCharacteristic.value)
        }
    }


}