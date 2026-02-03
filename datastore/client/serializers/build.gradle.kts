plugins {
  id("kmp.jvm.library")
  alias(libs.plugins.kotlinxSerialization)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(projects.datastore.client.models)
        api(libs.androidx.datastore)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.serialization.json.okio)
      }
    }
  }
}