package com.virtualworld.conectioninternet.data.conectionInternet

import com.virtualworld.multiplatformiot.ProductEmptyException
import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

const val NAME_DB_FIRESTORE = "usuarios"

class RemoteDataSource(private val firestore: FirebaseFirestore) {

    suspend fun getAllArduino(usuario: String): NetworkResponseState<List<ArduinoData>> {

        return firestore.collection(NAME_DB_FIRESTORE).document(usuario)
            .collection("arduinos")
            .snapshots
            .map { querySnapshot ->
                val listArduino = querySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.data<ArduinoData>().copy(nameArduino = documentSnapshot.id)
                }
                if (listArduino.isEmpty()) {
                    throw ProductEmptyException()
                } else {
                    NetworkResponseState.Success(listArduino)
                }
            }.first()
    }


    fun getArduino(usuario: String, name: String): Flow<NetworkResponseState<ArduinoData>> = flow {
        try {

            emit(NetworkResponseState.Loading)

            firestore.collection("usuarios").document(usuario)
                .collection("arduinos").document(name)
                .collection("objetos").snapshots.collect { querySnapshot ->

                    val objetos = querySnapshot.documents.map { documentSnapshot ->

                        val stateObject =
                            documentSnapshot.data<StateObject>() //.copy(keyObjeto = documentSnapshot.id)

                        Pair(documentSnapshot.id, stateObject)

                    }

                    val arduino = ArduinoData(nameArduino = name, objetos = objetos.toMap())

                    emit(NetworkResponseState.Success(arduino))
                }


        } catch (e: Exception) {
            emit(NetworkResponseState.Error(e))
        }
    }

    suspend fun updateArduinoState(arduinoData: ArduinoData): NetworkResponseState<StateObject> {
        return try {

            val objectStateRef = firestore.collection("usuarios").document("usuario1")
                .collection("arduinos").document(arduinoData.nameArduino!!).collection("objetos")
                .document(
                    arduinoData.objetos?.keys!!.first()
                )

            val objectState = objectStateRef.get().data<StateObject>()

            val updatedState = StateObject(objectState.nombre, !objectState.estado!!)

            objectStateRef.update(updatedState)

            NetworkResponseState.Success(updatedState)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }
    }

    suspend fun addArduino(arduino: ArduinoDomainModel) {

        val arduinoRef = firestore.collection("usuarios")
            .document("usuario1")
            .collection("arduinos")
            .document(arduino.name!!)


        // (Opcional) Guarda datos generales del Arduino
        arduinoRef.set(mapOf("name" to arduino.name!!, "active" to arduino.active))


        // Guarda cada estado como un documento en la subcolección "objetos"
        arduino.state1?.forEach { (key, stateObject) ->

            println(arduinoRef)

            val objetoRef = arduinoRef.collection("objetos").document(key)

            val data = mapOf(
                "nombre" to stateObject.nombre,
                "estado" to stateObject.estado
            )

            objetoRef.set(data)
        }
    }


}