plugins {
  id("kmp.jvm.library")
  alias(libs.plugins.kotlinxSerialization)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      // put your Multiplatform dependencies here
      implementation(libs.kotlinx.serialization.json)
    }
  }
}
