plugins {
  id("client.kmp.compose.feature.ui")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(projects.client.feature.lend.navigation)
      api(projects.client.feature.lend.presenter)
    }
  }
}