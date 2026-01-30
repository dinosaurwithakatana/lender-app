plugins {
  id("client.kmp.compose.feature.ui")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(projects.client.feature.home.navigation)
      api(projects.client.feature.home.presenter)
    }
  }
}