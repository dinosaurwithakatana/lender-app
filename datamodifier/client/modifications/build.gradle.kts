plugins {
  id("kmp.jvm.library")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.shared)

      implementation(projects.datamodifier.api)
      implementation(projects.client.network)
    }
  }
}
