import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.ksp)


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
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {

            //UI CORE

            //ID
            implementation(libs.koin.android)

            //NETWORK
            implementation(libs.ktor.client.android)

            implementation(libs.kstore.file)


        }



        commonMain.dependencies {

            implementation(projects.ui.core)
            implementation(projects.feature.conectionLocal)
            implementation(projects.feature.menu)

            //UI CORE

            //VIEWMODEL
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            //NAVIGATION
            implementation(libs.androidx.navigation.compose)

            //ID
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)
            //implementation(project.dependencies.platform(libs.koin.bom))

            //NETWORK
            implementation(libs.ktor.client.core)
            implementation(libs.coil.network.ktor)

            //COIL
            implementation(libs.coil.compose)


            implementation(libs.kstore)

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

        }


        wasmJsMain.dependencies {

            implementation(libs.kstore.storage)

        }


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
}

compose.desktop {
    application {
        mainClass = "com.virtualworld.multiplatformiot.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.virtualworld.multiplatformiot"
            packageVersion = "1.0.0"
        }
    }
}




