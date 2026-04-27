@file:OptIn(DelicateMetroGradleApi::class)

import dev.zacsweers.metro.gradle.DelicateMetroGradleApi

plugins {
  id("server.library")
  alias(libs.plugins.ktor)
  application
}

application {
  mainClass.set("dev.dwak.lender.ApplicationKt")

  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
  implementation(projects.datamodifier.implementation)
  implementation(projects.datamodifier.server.handlers)
  implementation(projects.models.api)
  implementation(projects.repos.server.implementation)
  implementation(projects.server.database)
  implementation(projects.shared)

  implementation(projects.server.common)
  implementation(projects.server.feature.auth)
  implementation(projects.server.feature.item)
  implementation(projects.server.feature.lend)
  implementation(projects.server.feature.groups)
  implementation(projects.server.feature.profile)

  implementation(libs.logback)
  implementation(libs.ktor.serverAuth)
  implementation(libs.ktor.serverAuth.apiKey)
  implementation(libs.ktor.serverCors)
  implementation(libs.ktor.serverDi)
  implementation(libs.ktor.serverCore)
  implementation(libs.ktor.serverRoutingOpenApi)
  implementation(libs.ktor.serverNetty)
  implementation(libs.ktor.serverContentNegotiation)
  implementation(libs.ktor.serialzationJson)

  testImplementation(libs.ktor.serverTestHost)
  testImplementation(libs.kotlin.testJunit)
}


val serverBuildsWeb = providers.gradleProperty("lender.serverBuildsWeb").map { it.toBoolean() }.getOrElse(true)

if (serverBuildsWeb) {
  tasks.register<Copy>("copyWasmToServer") {
    dependsOn(":client:composeApp:wasmJsBrowserDistribution")
    from("../../client/composeApp/build/dist/wasmJs/productionExecutable")
    into(layout.buildDirectory.dir("generated/wasmClient/resources/static"))
  }

  sourceSets {
    main {
      resources {
        srcDir(layout.buildDirectory.dir("generated/wasmClient/resources"))
      }
    }
  }

  tasks.named("processResources") {
    dependsOn(":server:app:copyWasmToServer")
  }
}