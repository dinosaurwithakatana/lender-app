plugins {
  id("kmp.android.library")
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(projects.models.client)
      }
    }
  }
}

