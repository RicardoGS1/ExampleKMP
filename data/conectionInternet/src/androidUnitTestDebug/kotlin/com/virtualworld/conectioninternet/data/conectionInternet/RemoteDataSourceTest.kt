package com.virtualworld.conectioninternet.data.conectionInternet



import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.QuerySnapshot
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test



class RemoteDataSourceTest {
    // Mock de FirebaseFirestore
    private lateinit var mockFirestore: FirebaseFirestore
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        mockFirestore = mockk(relaxed = true) // Mock con comportamiento por defecto
        remoteDataSource = RemoteDataSource(mockFirestore)
    }

    @Test
    fun `getAllStates should emit Loading state first`() = runBlocking {
        // Arrange
        val arduino = "arduino1"
        val stateObjects = listOf(StateObject(false))
        val mockQuerySnapshot = mockk<QuerySnapshot>()
        val mockDocument = mockk<DocumentSnapshot>()

        coEvery { mockQuerySnapshot.documents } returns listOf(mockDocument)
        coEvery { mockDocument.data<StateObject>() } returns stateObjects[0]
        coEvery { mockFirestore.collection(any()).document(any()).collection(any()).snapshots } returns flow { emit(mockQuerySnapshot) }

        // Act
        val flow = remoteDataSource.getAllStates(arduino)
        val resultList = flow.toList()//Transforma el flow en una lista

        // Assert
        assertEquals(NetworkResponseState.Loading, resultList[0])//Recupera el primer elemento del flow

    }

    @Test
    fun `getAllStates should emit Success state with data`() = runBlocking {
        // Arrange
        val arduino = "arduino1"
        val stateObjects = listOf(StateObject(false),)
        val mockQuerySnapshot = mockk<QuerySnapshot>()
        val mockDocument = mockk<DocumentSnapshot>()

        coEvery { mockQuerySnapshot.documents } returns listOf(mockDocument)
        coEvery { mockDocument.data<StateObject>() } returns stateObjects[0]
        coEvery { mockFirestore.collection(any()).document(any()).collection(any()).snapshots } returns flow { emit(mockQuerySnapshot) }

        // Act
        val flow = remoteDataSource.getAllStates(arduino)
        val resultList = flow.toList()//Transforma el flow en una lista

        // Assert
        assertTrue(resultList.size == 2)//Comprueba que hay dos elementos
        assertEquals(NetworkResponseState.Success(stateObjects), resultList[1]) // Verifica el segundo elemento
    }

    @Test
    fun `getAllStates should emit Error state with exception`() = runBlocking {
        // Arrange
        val arduino = "arduino1"
        val exception = Exception("Test exception")

        coEvery { mockFirestore.collection(any()).document(any()).collection(any()).snapshots } throws exception

        // Act
        val flow = remoteDataSource.getAllStates(arduino)
        val resultList = flow.toList()

        // Assert
        assertTrue(resultList.size == 2)
        assertEquals(NetworkResponseState.Error(exception), resultList[1])
    }

    @Test
    fun `getAllStates should emit error when states is empty`() = runBlocking {
        // Arrange
        val arduino = "arduino1"
        val mockQuerySnapshot = mockk<QuerySnapshot>()
        coEvery { mockQuerySnapshot.documents } returns listOf()
        coEvery { mockFirestore.collection(any()).document(any()).collection(any()).snapshots } returns flow { emit(mockQuerySnapshot) }

        // Act
        val flow = remoteDataSource.getAllStates(arduino)
        val resultList = flow.toList()

        // Assert
        assertTrue(resultList.size == 2)
        assertTrue(resultList[1] is NetworkResponseState.Error)
        val errorState = resultList[1] as NetworkResponseState.Error
        //assertTrue(errorState.exception is ProductEmptyException)
    }
}