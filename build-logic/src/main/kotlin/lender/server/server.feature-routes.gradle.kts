import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val libs = the<LibrariesForLibs>()

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("dev.zacsweers.metro")
}

metro {
  contributesAsInject.set(true)
}

kotlin {
  compilerOptions {
    freeCompilerArgs.set(listOf("-Xcontext-parameters"))
  }
}

dependencies {
  implementation(project(":server:common"))

  implementation(project(":datamodifier:api"))
  implementation(project(":datamodifier:server:modifications"))

  implementation(project(":models:api"))
  implementation(project(":repos:server:api"))
  implementation(project(":shared"))

  implementation(libs.ktor.serverAuth)
  implementation(libs.ktor.serverCore)
  implementation(libs.ktor.serverContentNegotiation)
  implementation(libs.ktor.serialzationJson)

  testImplementation(libs.ktor.serverTestHost)
  testImplementation(libs.kotlin.testJunit)
}
