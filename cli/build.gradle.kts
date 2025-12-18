@file:OptIn(DelicateMetroGradleApi::class)

import dev.zacsweers.metro.gradle.DelicateMetroGradleApi

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.metro)
    application
}

application {
    mainClass.set("dev.dwak.lender.cli.MainKt")
    applicationName = "lender-cli"
}

metro {
//    reportsDestination.set(layout.buildDirectory.dir("reports/metro"))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.datamodifier.implementation)
    implementation(projects.datamodifier.server)

    implementation(projects.database)
    implementation(projects.shared)

    implementation(libs.clikt)
}
