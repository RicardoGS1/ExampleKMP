package com.virtualworld.conectioninternet.data.conectionInternet

import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import dev.gitlive.firebase.firestore.CollectionReference
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.QuerySnapshot
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceTest {

    // 1. Mocks para cada nivel de la jerarquía de Firestore
    private val firestore: FirebaseFirestore = mockk()
    private val userDocRef: DocumentReference = mockk()
    private val arduinosColRef: CollectionReference = mockk()
    private val querySnapshot: QuerySnapshot = mockk()

    // 2. La clase que estamos probando
    private lateinit var remoteDataSource: RemoteDataSource

    // 3. Un dispatcher de test para controlar las corrutinas
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        // Inyectamos el dispatcher de test para controlar el `withContext(Dispatchers.IO)`
        Dispatchers.setMain(testDispatcher)
        remoteDataSource = RemoteDataSource(firestore)
    }

    @AfterTest
    fun tearDown() {
        // Limpiamos el dispatcher después de cada test
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllArduino cuando Firestore devuelve documentos, retorna Success con lista de ArduinoData`() = runTest {
        // 1. Arrange (Preparación)

        // a. Datos de prueba
        val arduinoDto1 = ArduinoData(nameArduino = "temp_id_1", active = true)
        val arduinoDto2 = ArduinoData(nameArduino = "temp_id_2", active = false)

        // b. Mocks de los documentos que devolverá Firestore
        val docSnapshot1: DocumentSnapshot = mockk {
            every { id } returns "arduino-uno" // El ID del documento real
            every { data<ArduinoData>() } returns arduinoDto1
        }
        val docSnapshot2: DocumentSnapshot = mockk {
            every { id } returns "esp32-cam" // El ID del documento real
            every { data<ArduinoData>() } returns arduinoDto2
        }

        // c. Simular la cadena de llamadas de la API de Firestore
        every { firestore.collection("usuarios") } returns arduinosColRef // Cambiado, la colección de usuarios es el primer nivel
        every { arduinosColRef.document("testUser") } returns userDocRef
        every { userDocRef.collection("arduinos") } returns arduinosColRef
        every { arduinosColRef.snapshots } returns flowOf(querySnapshot)
        every { querySnapshot.documents } returns listOf(docSnapshot1, docSnapshot2)

        // 2. Act (Acción)
        val result = remoteDataSource.getAllArduino("testUser")

        // 3. Assert (Verificación)
        assertTrue(result is NetworkResponseState.Success, "El resultado debería ser Success")

        val successResult = result as NetworkResponseState.Success
        assertEquals(2, successResult.result.size, "La lista debería contener 2 elementos")

        // Verificamos que los IDs de los documentos se han copiado correctamente
        assertEquals("arduino-uno", successResult.result[0].nameArduino)
        assertEquals("esp32-cam", successResult.result[1].nameArduino)
        assertEquals(true, successResult.result[0].active)
    }

    @Test
    fun `getAllArduino cuando Firestore devuelve una lista vacía, retorna Error`() = runTest {
        // 1. Arrange (Preparación)
        // Simulamos que la consulta a Firestore devuelve una lista vacía de documentos
        every { firestore.collection("usuarios") } returns arduinosColRef
        every { arduinosColRef.document("testUser") } returns userDocRef
        every { userDocRef.collection("arduinos") } returns arduinosColRef
        every { arduinosColRef.snapshots } returns flowOf(querySnapshot)
        every { querySnapshot.documents } returns emptyList()

        // 2. Act (Acción)
        val result = remoteDataSource.getAllArduino("testUser")

        // 3. Assert (Verificación)
        assertTrue(result is NetworkResponseState.Error, "El resultado debería ser Error")

        val errorResult = result as NetworkResponseState.Error
        assertEquals("No se encontro ningun elemento", errorResult.exception.message)
    }

    @Test
    fun `getAllArduino cuando Firestore lanza una excepcion, retorna Error`() = runTest {
        // 1. Arrange (Preparación)
        val expectedException = RuntimeException("Error de red simulado")
        // Simulamos que la llamada a .snapshots lanza una excepción
        coEvery { firestore.collection(any()).document(any()).collection(any()).snapshots } throws expectedException

        // 2. Act (Acción)
        val result = remoteDataSource.getAllArduino("testUser")

        // 3. Assert (Verificación)
        assertTrue(result is NetworkResponseState.Error, "El resultado debería ser Error")
        assertEquals(expectedException, (result as NetworkResponseState.Error).exception)
    }
}
