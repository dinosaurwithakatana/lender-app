plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.metro)
}

dependencies {
    api(projects.shared)
    api(projects.datamodifier.api)
    implementation(projects.database)
    api(projects.models.server)


    implementation(libs.argon2)
}
