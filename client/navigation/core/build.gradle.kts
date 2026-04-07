plugins {
  id("kmp.android.library")
  id("parcelize")
  alias(libs.plugins.kotlinxSerialization)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(libs.circuit.runtime)
      implementation(libs.circuit.foundation)
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
  }
}