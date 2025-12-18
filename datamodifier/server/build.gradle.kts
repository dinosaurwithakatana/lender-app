plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.metro)
}

group = "dev.dwak.lender"

dependencies {
//    api("commons-logging:commons-logging:1.2")

    api(projects.shared)
    api(projects.datamodifier.api)
    implementation(projects.database)
    api(projects.models.server)

    api(libs.argon2)
}
