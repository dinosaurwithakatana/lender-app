import dev.zacsweers.metro.gradle.ExperimentalMetroGradleApi

plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.metro)
  alias(libs.plugins.kotlinxSerialization)
  application
}

application {
  mainClass.set("dev.dwak.lender.cli.MainKt")
  applicationName = "lender-cli"
}

metro {
  @OptIn(ExperimentalMetroGradleApi::class)
  enableSuspendProviders = true
}

dependencies {
  implementation(projects.models.cli)
  implementation(projects.cli.commands)

  implementation(projects.datamodifier.implementation)
  implementation(projects.datamodifier.server.handlers)

  implementation(projects.datastore.client.serializers)

  implementation(projects.server.database)
  implementation(projects.shared)

  implementation(projects.repos.server.implementation)

  implementation(libs.clikt)
  implementation(libs.kotlinx.serialization.json)
}
