plugins {
  id("kmp.jvm.library")
  alias(libs.plugins.kotlinxSerialization)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.kotlinx.serialization.json)
      }
    }
  }
}