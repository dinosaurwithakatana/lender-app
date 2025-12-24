plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.metro)
}

dependencies {
  api(projects.shared)
  api(projects.datamodifier.api)
  api(projects.models.server)
}
