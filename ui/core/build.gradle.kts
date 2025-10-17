import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)

}

kotlin {


    androidTarget {
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
            baseName = "UiCore"
            isStatic = true
        }
    }

    jvm("desktop"){
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }


    sourceSets {

        val desktopMain by getting

        androidMain.dependencies {
            api(compose.preview)
            api(libs.androidx.activity.compose)

        }

        commonMain.dependencies {

            api(projects.domain.core)

            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)

            implementation(libs.kotlinx.serialization)

            //ID
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)

            //VIEWMODEL
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)


        }

        iosMain.dependencies {

        }


        desktopMain.dependencies {

            api(compose.desktop.currentOs)
            api(libs.kotlinx.coroutines.swing)

        }



    }
}

android {
    namespace = "com.virtualworld.multiplatformiot.ui.core"
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

    compose.resources {
        publicResClass = true
        generateResClass = always
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

    }
    ndkVersion = "28.0.12674087 rc2"
    buildToolsVersion = "35.0.0"
}
dependencies {
    implementation(libs.androidx.ui.text.google.fonts)
}
