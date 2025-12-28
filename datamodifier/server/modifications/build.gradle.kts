plugins {
  id("server.library")
}

dependencies {
  api(projects.shared)
  api(projects.datamodifier.api)
  api(projects.models.server)
}
