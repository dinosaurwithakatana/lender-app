plugins {
  id("client.kmp.compose.feature.ui")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(projects.client.feature.groups.navigation)
      api(projects.client.feature.groups.presenter)
    }
  }
}