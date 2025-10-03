
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {

    jvm("desktop"){
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "DomainConnectionBluetooth"
            isStatic = true
        }
    }

    sourceSets {

        commonMain.dependencies {

            implementation(projects.data.core)
            implementation(projects.domain.core)

            implementation(libs.kotlinx.coroutines.core)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            //SERIALISATION
            implementation(libs.kotlinx.serialization)
        }


    }
}


