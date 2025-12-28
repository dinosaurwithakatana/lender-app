plugins {
  id("server.library")
}

dependencies {
  api(projects.repos.server.api)
  api(projects.shared)
  api(projects.models.server)
  implementation(projects.database)
  implementation(libs.kotlinx.coroutines.core)
}
