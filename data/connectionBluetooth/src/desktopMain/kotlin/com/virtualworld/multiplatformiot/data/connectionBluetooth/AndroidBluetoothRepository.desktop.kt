package com.virtualworld.multiplatformiot.data.connectionBluetooth

import com.fazecast.jSerialComm.SerialPort
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothDeviceDomain
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.BluetoothRepository
import com.virtualworld.multiplatformiot.domain.connectionBluetooth.ResponseState
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomain
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

    actual override suspend fun getPairedDevices(): List<BluetoothDeviceDomain> =
        withContext(Dispatchers.IO) {
            try {
                SerialPort.getCommPorts()
//                    .filter {
//                        val name = it.descriptivePortName.lowercase()
//                        // Puedes hacer este filtro tan simple o complejo como necesites
//                        name.contains("bluetooth") || name.contains("blth") || name.contains("hc-05") || name.contains("arduino")
//                    }
                    .map { port ->
                        BluetoothDeviceDomain(
                            name = port.descriptivePortName,
                            address = port.systemPortPath.encodeURLParameter() // La dirección correcta para `connectToDevice` es codifica para evitar errores
                        )
                    }
            } catch (e: Exception) {
                println("[Desktop] Error listando emparejados via system_profiler: ${e.message}")
                emptyList()
            }
        }

    actual override suspend fun connectToDevice(address: String): ResponseState<Boolean> =
        withContext(Dispatchers.IO) {
            try {
               SerialPort.getCommPorts().forEach {println( it.systemPortPath) }
                // address esperado: ruta del puerto, p.ej. "/dev/tty.HC-05-DevB"
                val port = SerialPort.getCommPorts()
                    .firstOrNull { (it.systemPortPath ) == address.decodeURLPart() }
                    ?: return@withContext ResponseState.Error(Exception("Puerto serie no encontrado: $address"))



                // Configuración típica de HC-05: 9600-8-N-1 (ajusta si es diferente)
                port.baudRate = 9600
                port.numDataBits = 8
                port.numStopBits = SerialPort.ONE_STOP_BIT
                port.parity = SerialPort.NO_PARITY
                port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 5000, 2000)

                println("todo listo para inciar puerto")

                if (!port.openPort()) {
                    println("puero no se pudo abrir")
                    return@withContext ResponseState.Error(Exception("No se pudo abrir el puerto: $address"))
                }

                println("puerto iniciado")

                serialPort = port
                inputStream = port.inputStream
                outputStream = port.outputStream

                currentObservingArduinoName = port.descriptivePortName ?: address

                ResponseState.Success(true)
            } catch (e: Exception) {
                closeConnection()
                ResponseState.Error(e)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    actual override fun getAllStatesFlow(): Flow<ResponseState<ArduinoDomain>> {

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



    private suspend fun getAllStatesArduino(): ResponseState<ArduinoDomain> {


        if (serialPort == null || serialPort?.isOpen != true) {

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


            val arduinoDomain = ArduinoDomain(
                name = currentObservingArduinoName, active = true, state1 = states
            )
            ResponseState.Success(arduinoDomain)

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




//    private suspend fun getAllStatesArduino(): ResponseState<ArduinoDomain> {
//        if (serialPort == null || serialPort?.isOpen != true) {
//            return ResponseState.Error(Exception("No hay conexión con ningún dispositivo Arduino."))
//        }
//        return try {
//            // Solicitar estados actuales al Arduino
//            sendCommand("GET_STATES\n")
//
//            println("Comando enviado GET_STATES")
//
//            val states = readArduinoStates()
//            val arduinoDomain = ArduinoDomain(
//                name = currentObservingArduinoName, active = true, state1 = states
//            )
//            ResponseState.Success(arduinoDomain)
//        } catch (e: IOException) {
//            ResponseState.Error(Exception("Error leyendo estados del Arduino: ${e.message}", e))
//        } catch (e: Exception) {
//            ResponseState.Error(e)
//        }
//    }

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

    private fun getPairedBluetoothDevicesOnMac(): List<BluetoothDeviceDomain> {
        val result = mutableListOf<BluetoothDeviceDomain>()

        val process = ProcessBuilder(
            "/usr/sbin/system_profiler",
            "SPBluetoothDataType"
        )
            .redirectErrorStream(true)
            .start()

        val output = process.inputStream.bufferedReader().use { it.readText() }
        val exit = process.waitFor()
        if (exit != 0) throw Exception("system_profiler devolvió código $exit")

        // Parse simple: buscamos bloques que contengan "Paired: Yes" y extraemos Name/Address
        // Ejemplos de líneas típicas:
        //     Name: HC-06
        //     Address: 98-DA-60-12-34-56
        //     Paired: Yes
        //     Manufacturer: ...
        var currentName: String? = null
        var currentAddress: String? = null
        var currentPaired = false

        fun flushIfValid() {
            if (currentName != null || currentAddress != null) {
//                val name = currentName ?: (currentAddress ?: "Dispositivo Bluetooth")
//                val address = currentAddress ?: name
                result.add(
                    BluetoothDeviceDomain(
                        name = currentName?:"error",
                        address = currentAddress?:"error",
                        isConnected = currentPaired
                    )
                )
            }
            currentName = null
            currentAddress = null
            currentPaired = false
        }

        output.lineSequence().forEach { rawLine ->
            println("rawline"+rawLine)
            val line = rawLine.trim()
            // Los bloques de dispositivos suelen separarse por líneas en blanco o por encabezados
//            if (line.isEmpty()) {
//                flushIfValid()
//                return@forEach
//            }
            when {
//                line.startsWith("Name:") || line.startsWith("Nombre:") -> {
//                    currentName = line.substringAfter(":").trim()
//                }
                line.startsWith("Address:") -> {
                    // macOS usa guiones; mantenemos como address
                    currentAddress = line.substringAfter(":").trim()
                    flushIfValid()
                }
                line.startsWith("Paired:") || line.startsWith("Emparejado:") -> {
                    val value = line.substringAfter(":").trim()
                    currentPaired = value.equals("Yes", true) || value.equals("Sí", true) || value.equals("Si", true)
                }
                // Nuevo bloque detectado por encabezado "Devices:" u otro ⇒ vaciamos acumulado
                line.endsWith(":") && line != "Bluetooth:" -> {
                    currentName = line.substringBefore(":").trim()
                }
            }

        }
        // Último bloque
        //flushIfValid()

        return result
    }




}