package com.virtualworld.conectioninternet.data.conectionInternet

import com.virtualworld.multiplatformiot.ProductEmptyException
import com.virtualworld.multiplatformiot.data.core.dto.Arduino
import com.virtualworld.multiplatformiot.data.core.NetworkResponseState
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val firestore: FirebaseFirestore) {


    fun getAllArduino(usuario: String): Flow<NetworkResponseState<List<Arduino>>> = flow {
        try {

            emit(NetworkResponseState.Loading)

            firestore.collection("usuarios").document(usuario)
                .collection("arduinos").snapshots.collect { querySnapshot ->

                    val listArduino = querySnapshot.documents.map { documentSnapshot ->
                        documentSnapshot.data<Arduino>().copy(name = documentSnapshot.id)
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

    fun getArduino(usuario: String, name:String): Flow<NetworkResponseState<Arduino>> = flow {
        try {

            emit(NetworkResponseState.Loading)

            firestore.collection("usuarios").document(usuario)
                .collection("arduinos").document(name).snapshots.collect { querySnapshot ->

                    val arduino = querySnapshot.data<Arduino>().copy(name = name)


                        emit(NetworkResponseState.Success(arduino))
                    }


        } catch (e: Exception) {
            emit(NetworkResponseState.Error(e))
        }
    }


}