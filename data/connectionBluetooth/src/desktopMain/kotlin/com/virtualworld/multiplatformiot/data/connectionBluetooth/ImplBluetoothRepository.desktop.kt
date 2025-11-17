package com.virtualworld.multiplatformiot.data.connectionBluetooth

import com.fazecast.jSerialComm.SerialPort
import com.virtualworld.multiplatformiot.data.core.ResponseStateData
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothDeviceDomain
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothRepository
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseStateDomain
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.domain.core.models.StateObjectDomain
import io.ktor.http.decodeURLPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import io.ktor.http.encodeURLParameter
import java.io.BufferedReader
import java.io.InputStreamReader


actual class ImplBluetoothRepository : BluetoothRepository {

    // Puerto serie activo (SPP) asociado al dispositivo Bluetooth ya emparejado
    private var serialPort: SerialPort? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null


    private var currentObservingArduinoName: String? = null

    actual override suspend fun getPairedDevices(): ResponseStateData<List<ArduinoDomainModel>> =
        withContext(Dispatchers.IO) {

            try {
              val serialPort =  SerialPort.getCommPorts()

                if(serialPort.isNullOrEmpty()){
                    throw Exception("No se encontro ningun dispositivo verifique el estado del Bluetooth")
                }

                val mapSerialPort = serialPort.map { port ->
                        ArduinoDomainModel(
                            name = port.descriptivePortName,
                            address = port.systemPortPath.encodeURLParameter() // La dirección correcta para `connectToDevice` es codifica para evitar errores
                        )
                    }
                ResponseStateData.Success(mapSerialPort)

            } catch (e: Exception) {
                ResponseStateData.Error(e)
            }
        }

    actual override suspend fun connectToDevice(address: String): ResponseStateDomain<Boolean> =
        withContext(Dispatchers.IO) {

            try {
               SerialPort.getCommPorts().forEach {println( it.systemPortPath) }
                // address esperado: ruta del puerto, p.ej. "/dev/tty.HC-05-DevB"
                val port = SerialPort.getCommPorts()
                    .firstOrNull { (it.systemPortPath ) == address.decodeURLPart() }
                    ?: return@withContext ResponseStateDomain.Error(Exception("Puerto serie no encontrado: $address"))

                // Configuración típica de HC-05: 9600-8-N-1
                port.baudRate = 9600
                port.numDataBits = 8
                port.numStopBits = SerialPort.ONE_STOP_BIT
                port.parity = SerialPort.NO_PARITY
                port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 5000, 2000)


                if (!port.openPort()) {
                    return@withContext ResponseStateDomain.Error(Exception("No se pudo abrir el puerto: $address"))
                }

                serialPort = port
                inputStream = port.inputStream
                outputStream = port.outputStream

                currentObservingArduinoName = port.descriptivePortName ?: address

                ResponseStateDomain.Success(true)
            } catch (e: Exception) {
                closeConnection()
                ResponseStateDomain.Error(e)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    actual override fun getAllStatesFlow(): Flow<ResponseStateDomain<ArduinoDomainModel>> {

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


        if (serialPort == null || serialPort?.isOpen != true) {

            return ResponseStateDomain.Error(Exception("Not connected to any Arduino device."))

        }

        return try {
            // Podrías querer re-enviar el comando "GET_STATES" si es necesario
            // antes de cada lectura, o asumir que el Arduino envía actualizaciones
            // continuamente y solo necesitas leer.

            // El Arduino solo responde cuando se le pregunta:
            sendCommand("GET_STATES\n")

            val states = readArduinoStates(inputStream) // Tu función de lectura existente

            val arduinoDomainModel = ArduinoDomainModel(
                name = currentObservingArduinoName!!, active = true, states = states
            )
            ResponseStateDomain.Success(arduinoDomainModel)

        } catch (e: IOException) {

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


    private fun closeConnection() {
        try {
            inputStream?.close()
            outputStream?.close()
            if (serialPort?.isOpen == true) {
                serialPort?.closePort()
            }
        } catch (e: IOException) {
            println("Error cerrando puerto serie: ${e.message}")
        } finally {
            inputStream = null
            outputStream = null
            serialPort = null
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
        // No hay API directa; inferimos si existen puertos Bluetooth disponibles
        SerialPort.getCommPorts().any { (it.systemPortPath ?: "").startsWith("/dev/tty") }
    }


}