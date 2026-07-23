plugins {
  id("server.library")
}

dependencies {
  api(libs.commons.logging)
  api(libs.bcpkix.jdk18on)

  api(projects.shared)

  api(projects.datamodifier.api)
  api(projects.datamodifier.common.handlers)
  api(projects.datamodifier.server.modifications)

  implementation(projects.server.database)
  api(projects.models.server)

  api(libs.argon2)
}
