plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.metro)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.datamodifier.api)
    implementation(projects.datamodifier.server.modifications)

    implementation(projects.database)
    implementation(projects.shared)

    implementation(libs.clikt)
}
