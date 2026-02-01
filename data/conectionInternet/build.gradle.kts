
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
   // alias(libs.plugins.googleServices)

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
            baseName = "DataConectionInternet"
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

            //FIREBASE
            implementation(project.dependencies.platform(libs.android.firebase.bom))
            implementation(libs.firebase.firestore.ktx)
            implementation(libs.kotlinx.coroutines.test)



        }

        commonMain.dependencies {

            implementation(projects.data.core)
            implementation(projects.domain.conectionInternet)
            implementation(projects.domain.core)

            api(libs.ktor.client.core)
            api("io.ktor:ktor-http:2.3.11")


            implementation(libs.kotlinx.coroutines.core)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            //NETWORK
            implementation(project.dependencies.platform(libs.ktor.bom))
            implementation(libs.coil.network.ktor)


            //FIREBASE
            implementation(libs.gitlive.firebase.firestore)

            implementation(libs.kotlinx.serialization)
        }

        desktopMain.dependencies {




        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(libs.mockk)
                implementation(libs.kotlinx.coroutines.test)
            }
        }


//        androidUnitTest.dependencies {
//            implementation(libs.junit)
//            implementation(libs.mockk)
//            implementation(libs.kotlinx.coroutines.test)
//            implementation(libs.androidx.arch.core.testing)
//            implementation(libs.kotlin.test)
//        }


    }


}


android {
    namespace = "com.virtualworld.multiplatformiot.data.conectionInternet"
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
