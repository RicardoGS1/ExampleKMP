

plugins {
    alias(libs.plugins.kotlinMultiplatform)


}

kotlin {



    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "DomainConectionLocal"
            isStatic = true
        }
    }

    jvm("desktop")



    sourceSets {

        commonMain.dependencies {

            implementation(projects.data.conectionLocal)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }


    }
}


