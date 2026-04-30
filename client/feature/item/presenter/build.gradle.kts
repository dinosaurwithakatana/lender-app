plugins {
  id("client.kmp.compose.feature.presenter")
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.client.feature.item.navigation)
      }
    }
  }
}