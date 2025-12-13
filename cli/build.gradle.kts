plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.metro)
    application
}

application {
    mainClass.set("dev.dwak.lender.cli.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.database)
    implementation(projects.shared)
    implementation(libs.clikt)
}
