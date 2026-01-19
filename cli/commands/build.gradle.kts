plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.metro)
}

metro {
  contributesAsInject.set(true)
}

dependencies {
  implementation(projects.models.cli)
  implementation(projects.datamodifier.api)
  implementation(projects.datamodifier.server.modifications)

  implementation(projects.shared)

  implementation(projects.repos.server.api)

  implementation(libs.clikt)
  implementation(libs.mordant)
  implementation(libs.mordant.coroutines)
}
