package com.virtualworld.conectioninternet.data.conectionInternet

import com.virtualworld.multiplatformiot.ProductEmptyException
import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RemoteDataSource(private val firestore: FirebaseFirestore) {


//    fun getAllArduino(usuario: String): Flow<NetworkResponseState<List<ArduinoData>>> = flow {
//        try {
//
//            emit(NetworkResponseState.Loading)
//
//            firestore.collection("usuarios").document(usuario)
//                .collection("arduinos").snapshots.collect { querySnapshot ->
//
//                    val listArduino = querySnapshot.documents.map { documentSnapshot ->
//                        documentSnapshot.data<ArduinoData>().copy(nameArduino = documentSnapshot.id)
//                    }
//
//                    println(listArduino)
//
//                    if (listArduino.isEmpty()) {
//                        throw ProductEmptyException()
//                    } else {
//                        emit(NetworkResponseState.Success(listArduino))
//                    }
//                }
//
//        } catch (e: Exception) {
//            emit(NetworkResponseState.Error(e))
//        }
//    }

    fun getAllArduino(usuario: String): Flow<NetworkResponseState<List<ArduinoData>>> {

        val a = firestore.collection("usuarios").document(usuario)

        println(a)

       // return flow {  emit( NetworkResponseState.Loading )}

        return firestore.collection("usuarios").document(usuario)
            .collection("arduinos")
            .snapshots // Esto ya es un Flow<QuerySnapshot>
            .map { querySnapshot -> // Transforma cada emisión del QuerySnapshot
                val listArduino = querySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.data<ArduinoData>().copy(nameArduino = documentSnapshot.id)
                }
                println("Desde snapshots.map: $listArduino")
                if (listArduino.isEmpty()) {
                    // Opción 1: Emitir un estado de éxito con lista vacía
                    // NetworkResponseState.Success(emptyList<ArduinoData>())
                    // Opción 2: O si quieres tratar "vacío" como un caso especial que podría ser un error o estado diferente
                    throw ProductEmptyException() // Esto será capturado por .catch
                } else {
                    NetworkResponseState.Success(listArduino)
                }
            }





            //.onStart {  emit(NetworkResponseState.Loading) } // Emitir Loading al inicio de la recolección de este Flow
//            .catch { e -> // Capturar excepciones de la transformación o del Flow de snapshots
//                if (e is ProductEmptyException) {
//                    // Puedes manejar ProductEmptyException de forma diferente si quieres
//                    // Por ejemplo, emitir un estado específico para "vacío" o el error como está
//                    emit(NetworkResponseState.Error(e)) // O un estado específico: NetworkResponseState.Empty
//                } else {
//                    emit(NetworkResponseState.Error(e))
//                }
//            }
        // Opcionalmente, puedes añadir .flowOn(Dispatchers.IO) si la librería de Firestore
        // no garantiza que las callbacks/emisiones del snapshot ocurran en un hilo de fondo.
        // GitLive Firebase suele manejar esto bien.
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

    suspend fun addArduino(arduino: ArduinoDomainModel) {

        val arduinoRef = firestore.collection("usuarios")
            .document("usuario1")
            .collection("arduinos")
            .document(arduino.name!!)



        // (Opcional) Guarda datos generales del Arduino
        arduinoRef.set( mapOf("name" to arduino.name!!,"active" to arduino.active))



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