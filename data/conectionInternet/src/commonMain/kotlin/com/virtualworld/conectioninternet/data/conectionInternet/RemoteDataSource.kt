package com.virtualworld.conectioninternet.data.conectionInternet

import com.virtualworld.multiplatformiot.ProductEmptyException
import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val firestore: FirebaseFirestore) {


    fun getAllArduino(usuario: String): Flow<NetworkResponseState<List<ArduinoData>>> = flow {
        try {

            emit(NetworkResponseState.Loading)

            firestore.collection("usuarios").document(usuario)
                .collection("arduinos").snapshots.collect { querySnapshot ->

                    val listArduino = querySnapshot.documents.map { documentSnapshot ->
                        documentSnapshot.data<ArduinoData>().copy(nameArduino = documentSnapshot.id)
                    }

                    println(listArduino)

                    if (listArduino.isEmpty()) {
                        throw ProductEmptyException()
                    } else {
                        emit(NetworkResponseState.Success(listArduino))
                    }
                }

        } catch (e: Exception) {
            emit(NetworkResponseState.Error(e))
        }
    }

    fun getArduino(usuario: String, name: String): Flow<NetworkResponseState<ArduinoData>> = flow {
        try {

            emit(NetworkResponseState.Loading)

            firestore.collection("usuarios").document(usuario)
                .collection("arduinos").document(name)
                .collection("objetos").snapshots.collect { querySnapshot ->

                    val objetos = querySnapshot.documents.map { documentSnapshot ->

                        val stateObject = documentSnapshot.data<StateObject>() //.copy(keyObjeto = documentSnapshot.id)

                        Pair(documentSnapshot.id,stateObject)

                    }

                    val arduino = ArduinoData(nameArduino = name, objetos = objetos.toMap())

                    emit(NetworkResponseState.Success(arduino))
                }


        } catch (e: Exception) {
            emit(NetworkResponseState.Error(e))
        }
    }

    suspend fun updateArduinoState(arduinoData:ArduinoData): NetworkResponseState<StateObject> {
        return try {

            val objectStateRef = firestore.collection("usuarios").document("usuario1")
                .collection("arduinos").document(arduinoData.nameArduino!!).collection("objetos").document(
                    arduinoData.objetos?.keys!!.first())

            val objectState = objectStateRef.get().data<StateObject>()

            val updatedState = StateObject( objectState.nombre, !objectState.estado!!)

            objectStateRef.update(updatedState)

            NetworkResponseState.Success(updatedState)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }
    }
}