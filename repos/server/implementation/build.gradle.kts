import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.metro)
}

dependencies {
    api(projects.repos.server.api)
    api(projects.shared)
    api(projects.models.server)
    implementation(projects.database)
    implementation(libs.kotlinx.coroutines.core)
}
