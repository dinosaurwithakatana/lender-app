plugins {
  id("kmp.jvm.library")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.datamodifier.api)
    }
  }
}
