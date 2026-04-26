plugins {
  id("server.library")
}

dependencies {
  api(projects.shared)
  api(projects.models.server)
  implementation(projects.server.database)
  implementation(libs.kotlinx.coroutines.core)
}
