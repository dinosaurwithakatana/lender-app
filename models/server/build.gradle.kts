plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.metro)
}

dependencies {
    api(projects.shared)
}
