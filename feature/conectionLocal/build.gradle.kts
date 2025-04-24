import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)

}

kotlin {


    androidTarget {
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
            baseName = "FeatureConectionLocal"
            isStatic = true
        }
    }

    jvm("desktop")



    sourceSets {

        commonMain.dependencies {

            implementation(projects.domain.conectionLocal)

            //KOIN
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)

            //VIEWMODEL
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            //NAVIGATION
            implementation(libs.androidx.navigation.compose)

            //SERIALISATION
            implementation(libs.kotlinx.serialization)
        }


    }
}


android {
    namespace = "com.virtualworld.multiplatformiot.feature.connectionLocal"
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