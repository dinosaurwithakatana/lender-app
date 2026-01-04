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

dependencies {
  implementation(projects.models.cli)
  implementation(projects.cli.commands)

  implementation(projects.datamodifier.implementation)
  implementation(projects.datamodifier.server.handlers)

  implementation(projects.database)
  implementation(projects.shared)

  implementation(projects.repos.server.implementation)

  implementation(libs.clikt)
  implementation(libs.kotlinx.serialization.json)
}
