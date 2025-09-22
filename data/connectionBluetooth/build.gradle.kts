import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)

}

kotlin {


    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "DataConnectionBluetooth"
            isStatic = true
        }
    }

    jvm("desktop")



    sourceSets {

        androidMain.dependencies {
           // implementation(libs.android.bluetooth)
            implementation(libs.androidx.core.ktx)
        }

        commonMain.dependencies {

            implementation(projects.data.core)
            implementation(projects.domain.connectionBluetooth)
            implementation(projects.domain.core)

            implementation(libs.kotlinx.coroutines.core)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }




    }
}


android {
    namespace = "com.virtualworld.multiplatformiot.data.connectionBluetooth"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {

        minSdk = libs.versions.android.minSdk.get().toInt()


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