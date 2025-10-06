package com.virtualworld.multiplatformiot

import android.app.Application
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.google.firebase.FirebasePlatform
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.initialize
import org.koin.core.component.getScopeId

fun main() = application {

    FirebasePlatform.initializeFirebasePlatform(object : FirebasePlatform() {

        val storage = mutableMapOf<String, String>()

        override fun clear(key: String) {
            storage.remove(key)
        }

        override fun log(msg: String) = println("gms ggggggg")

        override fun retrieve(key: String): String? = storage[key]

        override fun store(key: String, value: String) = storage.set(key, value)

    })

    val options = FirebaseOptions(
        projectId = "multiplatformiot",
        applicationId = "1:909972065182:web:f1bfcb17a32005c9e9e48f",
        apiKey = "AIzaSyBqVQi5_zsr88KTKG4N9QcQ5GAsslD_Esc",
    )


    try {
        Firebase.initialize(Application(),options =  options)

    } catch (e: Throwable) {
        println("Error al iniciar GitLive Firebase para desktop: ${e.message}")
        e.printStackTrace() // ¡ESENCIAL para la depuración!
    }

    Window(onCloseRequest = ::exitApplication, title = "MultiplatformIoT") {
        App()
    }
}