plugins {
  id("kmp.jvm.library")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.shared)

      implementation(projects.datamodifier.api)
      implementation(projects.datamodifier.common.handlers)
      implementation(projects.datamodifier.client.modifications)

      implementation(projects.client.network)
    }
  }
}
