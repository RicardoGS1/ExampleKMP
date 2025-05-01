
plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {

    jvm()

    sourceSets {

        commonMain.dependencies {

            implementation(projects.data.core)

            implementation(libs.kotlinx.coroutines.core)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }


    }
}


