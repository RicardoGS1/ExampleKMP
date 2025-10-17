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

include(":domain:conectionLocal")
include(":domain:conectionInternet")
include(":domain:connectionBluetooth")
include(":domain:core")

include(":feature:conectionLocal")
include(":feature:menu")
include(":feature:conectionInternet")
include(":feature:connectionBluetooth")

