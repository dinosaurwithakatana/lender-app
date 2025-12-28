plugins {
  id("kmp.jvm.library")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(projects.datamodifier.api)
    }
  }
}
