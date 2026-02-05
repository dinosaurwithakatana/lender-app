plugins {
  id("kmp.jvm.library")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.shared)

      api(projects.datamodifier.common.handlers)
      api(projects.datamodifier.client.modifications)

      implementation(projects.client.network)
      implementation(projects.datastore.client.models)
    }
  }
}
