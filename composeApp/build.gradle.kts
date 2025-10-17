import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    id("com.google.gms.google-services")
    //libs.plugins.googleServices


}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }

//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        moduleName = "composeApp"
//        browser {
//            val rootDirPath = project.rootDir.path
//            val projectDirPath = project.projectDir.path
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
//                    static = (static ?: mutableListOf()).apply {
//                        // Serve sources to debug inside browser
//                        add(rootDirPath)
//                        add(projectDirPath)
//                    }
//                }
//            }
//        }
//        binaries.executable()
//    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {

            //UI CORE

            //ID
            implementation(libs.koin.android)

            //NETWORK
            implementation(libs.ktor.client.android)

            //KSTORE
            implementation(libs.kstore.file)

            //FIREBASE
            implementation(project.dependencies.platform(libs.android.firebase.bom))
            implementation(libs.firebase.firestore.ktx)

        }



        commonMain.dependencies {

            implementation(projects.ui.core)

            implementation(projects.feature.conectionLocal)
            implementation(projects.feature.conectionInternet)
            implementation(projects.feature.connectionBluetooth)
            implementation(projects.feature.menu)

            implementation(projects.data.conectionInternet)
            implementation(projects.data.connectionBluetooth)
            implementation(projects.data.conectionLocal)

            implementation(libs.kotlinx.coroutines.core)

            //UI CORE

            //VIEWMODEL
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            //NAVIGATION
            implementation(libs.androidx.navigation.compose)

            //ID
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)

            //NETWORK
            implementation(project.dependencies.platform(libs.ktor.bom))
            implementation(libs.ktor.client.core)
            implementation(libs.coil.network.ktor)

            //COIL
            implementation(libs.coil.compose)

            //KSTORE
            implementation(libs.kstore)

            //FIREBASE
            implementation(libs.gitlive.firebase.firestore)
           // implementation(libs.firebase.app)


        }


        iosMain.dependencies {

            //NETWORK
            implementation(libs.ktor.client.darwin)

            implementation(libs.kstore.file)

        }


        desktopMain.dependencies {

            // UI CORE

            //NETWORK
            implementation(libs.ktor.client.java)

            implementation(libs.kstore.file)

            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            // FIREBASE JVM support for Desktop
            implementation(libs.firebase.java.sdk)

            // SQLite JDBC nativo para Firestore (persistencia)
            implementation("org.xerial:sqlite-jdbc:3.45.3.0")

           // implementation("com.google.firebase:firebase-admin:9.2.0")

            // Logging binding for SLF4J (prevents 'No SLF4J providers were found')
           // runtimeOnly("org.slf4j:slf4j-simple:2.0.13")

            // Optional: Conscrypt for improved TLS/ALPN with gRPC (prevents 'Unable to find Conscrypt')
            // implementation("org.conscrypt:conscrypt-openjdk-uber:2.5.2")
        }


//        wasmJsMain.dependencies {
//
//            implementation(libs.kstore.storage)
//
//        }


    }

}


android {
    namespace = "com.virtualworld.multiplatformiot"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.virtualworld.multiplatformiot"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

    }
    ndkVersion = "28.0.12674087 rc2"
    buildToolsVersion = "35.0.0"
}

dependencies {
    debugImplementation(compose.uiTooling)
    implementation(libs.androidx.foundation.android)
}

compose.desktop {
    application {
        mainClass = "com.virtualworld.multiplatformiot.MainKt"

        // Asegurar disponibilidad de java.management y java.sql en runtime
        jvmArgs += listOf(
            "--add-modules=java.management,java.sql",
            "--enable-native-access=ALL-UNNAMED"
        )

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.virtualworld.multiplatformiot"
            packageVersion = "1.0.0"

            macOS {
                bundleID =
                    "com.virtualworld.multiplatformiot.desktop" // ID único del bundle para macOS
                // Opcional: Especificar icono
                // iconFile.set(project.file("src/desktopMain/resources/icon.icns"))
            }

            // Incluir módulos en la imagen de runtime
            modules("java.management", "java.sql","jdk.unsupported")
        }
    }
}




