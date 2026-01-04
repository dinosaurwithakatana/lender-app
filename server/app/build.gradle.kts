@file:OptIn(DelicateMetroGradleApi::class)

import dev.zacsweers.metro.gradle.DelicateMetroGradleApi
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.ktor)
  alias(libs.plugins.metro)
  application
}

application {
  mainClass.set("dev.dwak.lender.ApplicationKt")

  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
  docker {
    jreVersion.set(JavaVersion.valueOf("VERSION_${libs.versions.java.get()}"))
    localImageName.set("lender-api-server")
  }
}

metro {
  enableTopLevelFunctionInjection.set(true)
}

dependencies {
  implementation(projects.datamodifier.implementation)
  implementation(projects.datamodifier.server.handlers)
  implementation(projects.models.api)
  implementation(projects.repos.server.implementation)
  implementation(projects.database)
  implementation(projects.shared)

  implementation(projects.server.common)
  implementation(projects.server.feature.auth)
  implementation(projects.server.feature.groups)

  implementation(libs.logback)
  implementation(libs.ktor.serverAuth)
  implementation(libs.ktor.serverDi)
  implementation(libs.ktor.serverCore)
  implementation(libs.ktor.serverNetty)
  implementation(libs.ktor.serverContentNegotiation)
  implementation(libs.ktor.serialzationJson)

  testImplementation(libs.ktor.serverTestHost)
  testImplementation(libs.kotlin.testJunit)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
  freeCompilerArgs.set(listOf("-Xcontext-parameters"))
}