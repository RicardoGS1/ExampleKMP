
plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "DomainConectionsLocal"
            isStatic = true
        }
    }

    sourceSets {

        commonMain.dependencies {

            implementation(projects.data.core)

            implementation(libs.kotlinx.coroutines.core)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }


    }
}


