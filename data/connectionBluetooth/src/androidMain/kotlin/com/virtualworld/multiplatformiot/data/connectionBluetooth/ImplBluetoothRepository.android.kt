package com.virtualworld.multiplatformiot.data.connectionBluetooth


import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothDeviceDomain
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothRepository
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseState
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.domain.core.models.StateObjectDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.koin.mp.KoinPlatform.getKoin
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.util.UUID


actual class ImplBluetoothRepository : BluetoothRepository {


    val context: Context = getKoin().get()

    // UUID para SPP (Serial Port Profile) - estándar para comunicación serie Bluetooth
    private val SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    private val bluetoothManager: BluetoothManager by lazy {
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    }

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        bluetoothManager.adapter
    }

    // Variables para la conexión Bluetooth
    private var bluetoothSocket: BluetoothSocket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null


    private var currentObservingArduinoName: String? = null


    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    actual override suspend fun getPairedDevices(): List<ArduinoDomainModel> {


        return withContext(Dispatchers.IO) {

            if (!hasBluetoothPermission()) {
                return@withContext emptyList()
            }

            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices

            val devices = mutableListOf<ArduinoDomainModel>()


            pairedDevices?.forEach { device ->
                devices.add(
                    ArduinoDomainModel(
                        name = device.name ?: "Unknown Device",
                        address = device.address,
                        isConnected = true // Por ahora asumimos que no está conectado
                    )
                )
            }

            devices

        }
    }


    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    actual override suspend fun connectToDevice(address: String): ResponseState<Boolean> {

        return withContext(Dispatchers.IO) {
            try {
                // Verificar permisos
                if (!hasBluetoothPermission()) {
                    return@withContext ResponseState.Error(Exception("Bluetooth permissions not granted"))
                }

                // Verificar si Bluetooth está habilitado
                if (!isBluetoothEnabled()) {
                    return@withContext ResponseState.Error(Exception("Bluetooth is not enabled"))
                }

                // Obtener el dispositivo por dirección
                val device = bluetoothAdapter?.getRemoteDevice(address)
                    ?: return@withContext ResponseState.Error(Exception("Device not found"))

                println("paso ai algo raro")

                // Crear socket Bluetooth
                bluetoothSocket = device.createRfcommSocketToServiceRecord(SPP_UUID)
                bluetoothSocket?.connect()

                // Obtener streams de entrada y salida
                inputStream = bluetoothSocket?.inputStream
                outputStream = bluetoothSocket?.outputStream

                currentObservingArduinoName = device.name ?: "Arduino Device"

                ResponseState.Success(true)

            } catch (e: Exception) {
                // Cerrar conexión en caso de error
                closeConnection()
                ResponseState.Error(e)
            }
        }

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    actual override fun getAllStatesFlow(): Flow<ResponseState<ArduinoDomainModel>> {

        // flatMapLatest se usa para que si refreshTrigger emite un nuevo valor,
        // la operación interna (fetchCurrentStates) se cancele y se reinicie.
        // onStart emite un Unit para cargar los datos la primera vez que el Flow se colecta.


        return flow {
            while (true) {

                val currentState = getAllStatesArduino()
                // Creamos un Flow simple que emite el resultado de la función suspend
                emit(currentState)

                if (currentState is ResponseState.Error) {
                    break
                }

                delay(1000)


            }
        }.flowOn(Dispatchers.IO).distinctUntilChanged()

    }


    actual override suspend fun sendStateCommand(stateNumber: String) {
        val command = "${stateNumber}\n"
        try {

            sendCommand(command)

        } catch (e: Exception) {
            throw e
        }
    }


    private suspend fun getAllStatesArduino(): ResponseState<ArduinoDomainModel> {


        if (bluetoothSocket == null || bluetoothSocket?.isConnected != true) {

            return ResponseState.Error(Exception("Not connected to any Arduino device."))

        }

        return try {
            // Podrías querer re-enviar el comando "GET_STATES" si es necesario
            // antes de cada lectura, o asumir que el Arduino envía actualizaciones
            // continuamente y solo necesitas leer.

            // El Arduino solo responde cuando se le pregunta:
            sendCommand("GET_STATES\n")

            println("4")



            val states = readArduinoStates(inputStream) // Tu función de lectura existente

            println("6")


            val arduinoDomainModel = ArduinoDomainModel(
                name = currentObservingArduinoName!!, active = true, state1 = states
            )
            ResponseState.Success(arduinoDomainModel)

        } catch (e: IOException) {
            // Errores de IO específicos de la lectura/escritura
            // Podrías querer cerrar la conexión aquí si el error es grave
            // closeConnection() // Considera las implicaciones

            ResponseState.Error(
                Exception(
                    "Error reading Arduino states: ${e.message}", e
                )
            )

        }
    }


    private suspend fun sendCommand(command: String) {

        return withContext(Dispatchers.IO) {

            try {
                outputStream?.write(command.toByteArray())
                outputStream?.flush()
            } catch (e: IOException) {
                throw Exception("Error sending command: ${e.message}")
            }
            println("3")
        }
    }

    private fun readArduinoStates(inputStream: InputStream?): Map<String, StateObjectDomain> {

        if (inputStream == null) return emptyMap()

        // 1. Envolver el InputStream para lectura de líneas
        // Usamos InputStreamReader para manejar la codificación de caracteres (UTF-8 es común)
        // y BufferedReader para el método readLine()
        val reader = BufferedReader(InputStreamReader(inputStream))
        val states = mutableMapOf<String, StateObjectDomain>()
        var readingStates = false // Bandera para saber si estamos dentro de la sección
        val timeout = 5000L // 5 segundos de timeout
        val startTime = System.currentTimeMillis()

        try {
            // 2. Bucle principal de lectura línea por línea
            while (System.currentTimeMillis() - startTime < timeout) {
                // readLine() es un método bloqueante, pero es el más fiable
                // para datos basados en texto con delimitadores de línea (\n).
                // Lo hacemos no-bloqueante comprobando primero si hay datos.

               println (inputStream.available())
                if (inputStream.available() > 0 || readingStates) {
                    val line = reader.readLine() // Esto bloqueará hasta que haya una línea completa o timeout (depende de la implementación subyacente)

                    println((inputStream.available() > 0).toString() + "j" + "" + readingStates)

                    if (line == null) {
                        // Si readLine devuelve null, la conexión se cerró inesperadamente
                        throw IOException("Connection closed while reading.")
                    }

                    val trimmedLine = line.trim()
                    println("Received line: $trimmedLine") // Log de depuración

                    when (trimmedLine) {
                        "CURRENT_STATES" -> {
                            readingStates = true
                            continue // Saltar al inicio del bucle
                        }
                        "END_CURRENT_STATES" -> {
                            readingStates = false
                            break // Hemos terminado de leer, salir del bucle while
                        }
                        else -> {
                            // 3. Parsing de la línea si estamos dentro de la sección
                            if (readingStates && trimmedLine.contains("state") && trimmedLine.contains(":")) {
                                val parts = trimmedLine.split(":")
                                if (parts.size == 2) {
                                    val stateName = parts[0].trim()
                                    // Asegúrate de manejar el caso donde Arduino puede enviar caracteres extra
                                    val stateValue = parts[1].trim().toIntOrNull()

                                    if (stateValue != null) {
                                        states[stateName] = StateObjectDomain(
                                            nombre = stateName,
                                            estado = stateValue == 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Si no hay datos, esperamos un poco y comprobamos el timeout
                    Thread.sleep(50)
                }
            }

            if (readingStates) {
                // Esto significa que el timeout ocurrió antes de recibir END_CURRENT_STATES
                throw Exception("Timeout: END_CURRENT_STATES not received.")
            }


        } catch (e: IOException) {
            throw Exception("Error de lectura/conexión: ${e.message}")
        } catch (e: Exception) {
            throw e // Relanzar cualquier otro error de parsing o timeout
        }

        println("Parsing complete. Read ${states.size} states.")
        return states
    }


//    private fun readArduinoStates(): Map<String, StateObjectDomain> {
//        val states = mutableMapOf<String, StateObjectDomain>()
//        val response = StringBuilder()
//
//        try {
//            // Leer respuesta línea por línea con timeout
//            val buffer = ByteArray(1024)
//            var bytesRead: Int
//            var timeout = 5000 // 5 segundos de timeout
//            var startTime = System.currentTimeMillis()
//
//            // Esperar a que llegue la respuesta completa
//            Thread.sleep(500) // Dar tiempo al Arduino para responder
//
//            //(inputStream?.available() ?: 0) > 0 ||
//
//            while (
//                (System.currentTimeMillis() - startTime) < timeout
//            ) {
//                if (inputStream?.available() ?: 0 > 0) {
//                    bytesRead = inputStream?.read(buffer) ?: 0
//                    if (bytesRead > 0) {
//                        response.append(String(buffer, 0, bytesRead))
//                        startTime =
//                            System.currentTimeMillis() // Reset timeout cuando recibimos datos
//                    }
//                } else {
//                    Thread.sleep(100) // Pequeña pausa para no saturar la CPU
//                }
//            }
//
//            // Parsear la respuesta
//            val responseText = response.toString()
//            println("Arduino response: $responseText")
//
//            // Buscar la sección de estados actuales
//            val startIndex = responseText.indexOf("CURRENT_STATES")
//            val endIndex = responseText.indexOf("END_CURRENT_STATES")
//
//            if (startIndex != -1 && endIndex != -1) {
//                val statesSection = responseText.substring(startIndex, endIndex)
//                val lines = statesSection.split("\n")
//
//                for (line in lines) {
//                    if (line.contains("state") && line.contains(":")) {
//                        val parts = line.split(":")
//                        if (parts.size == 2) {
//                            val stateName = parts[0].trim()
//                            val stateValue = parts[1].trim().toIntOrNull()
//
//                            if (stateValue != null) {
//                                states[stateName] = StateObjectDomain(
//                                    nombre = stateName, //state1
//                                    estado = stateValue == 1
//                                )
//                            }
//                        }
//                    }
//                }
//            } else {
//
//                throw Exception("Error de formato")
//
//            }
//
//        } catch (e: IOException) {
//            throw Exception("Error reading Arduino response: ${e.message}")
//        }
//
//        println("5")
//        return states
//    }


    private fun closeConnection() {
        try {
            inputStream?.close()
            outputStream?.close()
            bluetoothSocket?.close()
        } catch (e: IOException) {
            println("Error closing Bluetooth connection: ${e.message}")
        } finally {
            inputStream = null
            outputStream = null
            bluetoothSocket = null
        }
    }


    actual override suspend fun disconnectFromDevice(address: String): Boolean =
        withContext(Dispatchers.IO) {
            try {
                closeConnection()
                true
            } catch (e: Exception) {
                false
            }
        }

    actual override suspend fun isBluetoothEnabled(): Boolean = withContext(Dispatchers.IO) {
        bluetoothAdapter?.isEnabled ?: false
    }

    private fun hasBluetoothPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Para versiones anteriores a Android 12
        }
    }


}

