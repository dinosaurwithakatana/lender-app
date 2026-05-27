plugins {
  id("kmp.android.library")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.datamodifier.api)
      implementation(projects.models.client)
    }
  }
}
