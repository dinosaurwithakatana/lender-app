@file:OptIn(DelicateMetroGradleApi::class)

import dev.zacsweers.metro.gradle.DelicateMetroGradleApi
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("server.library")
}

dependencies {
  implementation(libs.ktor.serverCore)
  testImplementation(libs.ktor.serverTestHost)
  testImplementation(libs.kotlin.testJunit)
}
