plugins {
  id("server.library")
}

dependencies {
  api("commons-logging:commons-logging:1.3.5")
  api("org.bouncycastle:bcpkix-jdk18on:1.84")

  api(projects.shared)

  api(projects.datamodifier.api)
  api(projects.datamodifier.common.handlers)
  api(projects.datamodifier.server.modifications)

  implementation(projects.database)
  api(projects.models.server)

  api(libs.argon2)
}
