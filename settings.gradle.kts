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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
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

include(
  ":cli:app",
  ":cli:commands",
  ":client:android",
  ":client:feature:auth:ui",
  ":client:feature:auth:navigation",
  ":client:feature:auth:presenter",
  ":client:navigation:core",
  ":client:navigation:integration",
  ":client:composeApp",
  ":database",
  ":datamodifier:api",
  ":datamodifier:common:handlers",
  ":datamodifier:implementation",
  ":datamodifier:client:modifications",
  ":datamodifier:client:handlers",
  ":datamodifier:server:modifications",
  ":datamodifier:server:handlers",
  ":models:api",
  ":models:cli",
  ":models:server",
  ":client:network",
  ":repos:client:api",
  ":repos:server:api",
  ":repos:server:implementation",
  ":server:app",
  ":server:feature:auth",
  ":server:feature:groups",
  ":server:feature:item",
  ":server:feature:lend",
  ":server:feature:profile",
  ":server:common",
  ":shared",
)

includeBuild("build-logic")