rootProject.name = "MultiplatformIoT"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":ui:core")

include(":data:core")
include(":data:conectionLocal")
include(":data:conectionInternet")
include(":data:connectionBluetooth")
include(":data:connectionBLE")
include(":data:login")

include(":domain:conectionLocal")
include(":domain:conectionInternet")
include(":domain:connectionBluetooth")
include(":domain:connectionBLE")
include(":domain:core")
include(":domain:login")

include(":feature:conectionLocal")
include(":feature:login")
include(":feature:menu")
include(":feature:conectionInternet")
include(":feature:connectionBluetooth")
include(":feature:connectionBLE")

