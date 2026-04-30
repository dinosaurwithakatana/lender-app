plugins {
  id("client.kmp.compose.feature.ui")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(projects.client.feature.item.navigation)
      api(projects.client.feature.item.presenter)
    }
  }
}