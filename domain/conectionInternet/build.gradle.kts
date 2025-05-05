
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {

    jvm()

    sourceSets {

        commonMain.dependencies {

            implementation(projects.data.core)

            implementation(libs.kotlinx.coroutines.core)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            //SERIALISATION
            implementation(libs.kotlinx.serialization)
        }


    }
}


