rootProject.name = "lender_app"
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

include(":cli:app")
include(":cli:commands")
include(":composeApp")
include(":database")
include(":datamodifier:api")
include(":datamodifier:implementation")
include(":datamodifier:server")
include(":models:api")
include(":models:server")
include(":repos:server:api")
include(":repos:server:implementation")
include(":server:app")
include(":server:feature:auth")
include(":server:common")
include(":shared")
