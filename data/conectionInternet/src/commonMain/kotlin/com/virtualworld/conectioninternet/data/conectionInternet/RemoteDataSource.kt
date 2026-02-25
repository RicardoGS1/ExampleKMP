package com.virtualworld.conectioninternet.data.conectionInternet

import com.virtualworld.multiplatformiot.data.core.ResponseStateData
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import com.virtualworld.multiplatformiot.domain.login.repository.AuthRepository
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

const val NAME_DB_FIRESTORE = "usuarios"

class RemoteDataSource(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) {

    private fun currentUid(): String =
        authRepository.currentUser?.uid ?: throw Exception("Usuario no autenticado")

    suspend fun getAllArduino(): ResponseStateData<List<ArduinoData>> {

        return withContext(Dispatchers.IO) {
            try {
                val uid = currentUid()
                val querySnapshot = firestore.collection(NAME_DB_FIRESTORE)
                    .document(uid)
                    .collection("arduinos")
                    .get(Source.SERVER)

                val listArduino = querySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.data<ArduinoData>()
                        .copy(nameArduino = documentSnapshot.id)
                }

                ResponseStateData.Success(listArduino)

            } catch (e: Exception) {
                ResponseStateData.Error(e)
            }
        }
    }


    fun getArduino(name: String): Flow<ResponseStateData<ArduinoData>> = flow {
        try {
            val uid = currentUid()
            firestore.collection(NAME_DB_FIRESTORE).document(uid).collection("arduinos")
                .document(name).collection("objetos").snapshots.collect { querySnapshot ->

                    val objetos = querySnapshot.documents.map { documentSnapshot ->

                        val stateObject = documentSnapshot.data<StateObject>()

                        Pair(documentSnapshot.id, stateObject)
                    }

                    val arduino = ArduinoData(nameArduino = name, objetos = objetos.toMap())

                    emit(ResponseStateData.Success(arduino))
                }

        } catch (e: Exception) {
            emit(ResponseStateData.Error(e))
        }
    }

    suspend fun updateArduinoState(arduinoData: ArduinoData): ResponseStateData<StateObject> {
        return try {
            val uid = currentUid()
            val objectStateRef =
                firestore.collection(NAME_DB_FIRESTORE).document(uid).collection("arduinos")
                    .document(arduinoData.nameArduino).collection("objetos").document(
                        arduinoData.objetos?.keys!!.first()
                    )

            val objectState = objectStateRef.get().data<StateObject>()

            val updatedState = StateObject(objectState.nombre, !objectState.estado!!)

            objectStateRef.update(updatedState)

            ResponseStateData.Success(updatedState)
        } catch (e: Exception) {
            ResponseStateData.Error(e)
        }
    }

    suspend fun addArduino(arduino: ArduinoDomainModel) {

        val uid = currentUid()
        val arduinoRef =
            firestore.collection(NAME_DB_FIRESTORE).document(uid).collection("arduinos")
                .document(arduino.name!!)


        // (Opcional) Guarda datos generales del Arduino
        arduinoRef.set(mapOf("name" to arduino.name!!, "active" to arduino.active))


        // Guarda cada estado como un documento en la subcolección "objetos"
        arduino.states?.forEach { (key, stateObject) ->

            println(arduinoRef)

            val objetoRef = arduinoRef.collection("objetos").document(key)

            val data = mapOf(
                "nombre" to stateObject.nombre, "estado" to stateObject.estado
            )

            objetoRef.set(data)
        }
    }


}