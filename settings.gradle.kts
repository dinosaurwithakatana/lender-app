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
    maven("https://redirector.kotlinlang.org/maven/bootstrap")
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/")
    maven("https://packages.jetbrains.team/maven/p/kt/dev/")
  }
}

dependencyResolutionManagement {
  repositoriesMode = RepositoriesMode.PREFER_PROJECT
  repositories {
    google {
      mavenContent {
        includeGroupAndSubgroups("androidx")
        includeGroupAndSubgroups("com.android")
        includeGroupAndSubgroups("com.google")
      }
    }
    mavenCentral()
    maven("https://redirector.kotlinlang.org/maven/bootstrap")
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/")
    maven("https://packages.jetbrains.team/maven/p/kt/dev/")
  }
}

include(":cli:app")
include(":cli:commands")
include(":client:android")
include(":client:feature:auth:ui")
include(":client:navigation:core")
include(":client:composeApp")
include(":database")
include(":datamodifier:api")
include(":datamodifier:common:handlers")
include(":datamodifier:implementation")
include(":datamodifier:client:modifications")
include(":datamodifier:client:handlers")
include(":datamodifier:server:modifications")
include(":datamodifier:server:handlers")
include(":models:api")
include(":models:server")
include(":client:network")
include(":repos:client:api")
include(":repos:server:api")
include(":repos:server:implementation")
include(":server:app")
include(":server:feature:auth")
include(":server:feature:groups")
include(":server:feature:profile")
include(":server:common")
include(":shared")

includeBuild("build-logic")