@file:OptIn(DelicateMetroGradleApi::class)

import dev.zacsweers.metro.gradle.DelicateMetroGradleApi
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.metro)
}

metro {
    contributesAsInject.set(true)
}

dependencies {
    implementation(projects.server.common)

    implementation(projects.datamodifier.api)
    implementation(projects.datamodifier.server.modifications)

    implementation(projects.models.api)
    implementation(projects.repos.server.api)
    implementation(projects.shared)

    implementation(libs.ktor.serverAuth)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverContentNegotiation)
    implementation(libs.ktor.serialzationJson)

    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xcontext-parameters"))
}