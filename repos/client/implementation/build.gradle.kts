plugins {
  id("kmp.jvm.library")
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(projects.repos.client.api)
        implementation(projects.datastore.client.models)
      }
    }
  }
}

