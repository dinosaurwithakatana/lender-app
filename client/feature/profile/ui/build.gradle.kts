plugins {
  id("client.kmp.compose.feature.ui")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(projects.client.feature.profile.navigation)
      api(projects.client.feature.profile.presenter)
    }
  }
}
