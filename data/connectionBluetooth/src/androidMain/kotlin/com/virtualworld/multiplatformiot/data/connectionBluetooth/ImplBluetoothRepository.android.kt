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
import com.virtualworld.multiplatformiot.data.core.ResponseStateData
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothRepository
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseStateDomain
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
    actual override suspend fun getPairedDevices(): ResponseStateData<List<ArduinoDomainModel>> {


        return withContext(Dispatchers.IO) {

            try {

                if (!hasBluetoothPermission()) {
                    throw Exception("Bluetooth permissions not granted")
                }

                // Verificar si Bluetooth está habilitado
                if (!isBluetoothEnabled()) {
                    throw Exception("Bluetooth is not enabled")
                }

                val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices

                if(pairedDevices.isNullOrEmpty()){
                    throw Exception("No se encontro ningun dispositivo")
                }

                val devices = mutableListOf<ArduinoDomainModel>()


                pairedDevices.forEach { device ->
                    devices.add(
                        ArduinoDomainModel(
                            name = device.name ?: "Unknown Device",
                            address = device.address,
                            active = null
                        )
                    )
                }

                ResponseStateData.Success(devices)

            } catch (e: Exception) {
                ResponseStateData.Error(e)
            }


        }
    }


    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    actual override suspend fun connectToDevice(address: String): ResponseStateDomain<Boolean> {

        return withContext(Dispatchers.IO) {
            try {

                // Verificar si Bluetooth está habilitado
                if (!isBluetoothEnabled()) {
                    return@withContext ResponseStateDomain.Error(Exception("Bluetooth is not enabled"))
                }

                // Obtener el dispositivo por dirección
                val device = bluetoothAdapter?.getRemoteDevice(address)
                    ?: throw Exception("Device not found address incorrect")


                // Crear socket Bluetooth
                bluetoothSocket = device.createRfcommSocketToServiceRecord(SPP_UUID)
                bluetoothSocket?.connect()

                // Obtener streams de entrada y salida
                inputStream = bluetoothSocket?.inputStream
                outputStream = bluetoothSocket?.outputStream

                currentObservingArduinoName = device.name ?: "Arduino Device"

                ResponseStateDomain.Success(true)

            } catch (e: Exception) {
                closeConnection()
                ResponseStateDomain.Error(Exception("No se pudo establecer connexion con el dispositivo.Error " + e.message) )
            }
        }

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    actual override fun getAllStatesFlow(): Flow<ResponseStateDomain<ArduinoDomainModel>> {

        // flatMapLatest se usa para que si refreshTrigger emite un nuevo valor,
        // la operación interna (fetchCurrentStates) se cancele y se reinicie.
        // onStart emite un Unit para cargar los datos la primera vez que el Flow se colecta.

        return flow {
            while (true) {

                val currentState = getAllStatesArduino()
                // Creamos un Flow simple que emite el resultado de la función suspend
                emit(currentState)

                if (currentState is ResponseStateDomain.Error) {
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


    private suspend fun getAllStatesArduino(): ResponseStateDomain<ArduinoDomainModel> {


        if (bluetoothSocket == null || bluetoothSocket?.isConnected != true) {

            return ResponseStateDomain.Error(Exception("Not connected to any Arduino device."))

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
                name = currentObservingArduinoName!!, active = true, states = states
            )
            ResponseStateDomain.Success(arduinoDomainModel)

        } catch (e: IOException) {
            // Errores de IO específicos de la lectura/escritura
            // Podrías querer cerrar la conexión aquí si el error es grave
            // closeConnection() // Considera las implicaciones

            ResponseStateDomain.Error(
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

        // Envolver el InputStream para lectura de líneas
        // Usamos InputStreamReader para manejar la codificación de caracteres (UTF-8 es común)
        // y BufferedReader para el método readLine()
        val reader = BufferedReader(InputStreamReader(inputStream))
        val states = mutableMapOf<String, StateObjectDomain>()
        var readingStates = false // Bandera para saber si estamos dentro de la sección
        val timeout = 5000L // 5 segundos de timeout
        val startTime = System.currentTimeMillis()

        try {
            // Bucle principal de lectura línea por línea
            while (System.currentTimeMillis() - startTime < timeout) {
                // readLine() es un método bloqueante, pero es el más fiable
                // para datos basados en texto con delimitadores de línea (\n).
                // Lo hacemos no-bloqueante comprobando primero si hay datos.

                println(inputStream.available())
                if (inputStream.available() > 0 || readingStates) {
                    val line =
                        reader.readLine() // Esto bloqueará hasta que haya una línea completa o timeout (depende de la implementación subyacente)

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
                            //  Parsing de la línea si estamos dentro de la sección
                            if (readingStates && trimmedLine.contains("state") && trimmedLine.contains(
                                    ":"
                                )
                            ) {
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

