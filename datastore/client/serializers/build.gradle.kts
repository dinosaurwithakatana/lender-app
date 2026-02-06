plugins {
  id("kmp.jvm.library")
  alias(libs.plugins.kotlinxSerialization)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(projects.datastore.client.models)
        api(libs.androidx.datastore.core.okio)
        implementation(libs.kotlinx.io.okio)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.serialization.json.okio)
      }
    }
  }
}