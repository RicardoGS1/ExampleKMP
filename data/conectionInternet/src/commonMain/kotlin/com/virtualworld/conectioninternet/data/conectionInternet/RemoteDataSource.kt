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
                        documentSnapshot.data<StateObject>().copy(keyObjeto = documentSnapshot.id)
                    }

                    val arduino = ArduinoData(nameArduino = name, objetos = objetos)

                    println(arduino)


                    emit(NetworkResponseState.Success(arduino))
                }


        } catch (e: Exception) {
            emit(NetworkResponseState.Error(e))
        }
    }

    suspend fun updateArduinoState(usuario: String, arduinoName: String, key: String, newValue: Boolean): NetworkResponseState<StateObject> {
        return try {

            val arduinoRef = firestore.collection("usuarios").document(usuario)
                .collection("arduinos").document(arduinoName).collection("objetos").document(key)

            val arduino = arduinoRef.get().data<StateObject>()

            val updatedState = StateObject(arduino.keyObjeto, arduino.nombre, !arduino.estado!!)

            arduinoRef.update(updatedState)

            NetworkResponseState.Success(updatedState)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }
    }
}