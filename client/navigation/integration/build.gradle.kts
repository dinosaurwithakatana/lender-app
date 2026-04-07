plugins {
  id("client.kmp.android.compose.library")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(libs.androidx.lifecycle.viewmodelCompose)
      implementation(libs.androidx.lifecycle.runtimeCompose)
      api(libs.circuit.foundation)

      api(projects.client.navigation.core)
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
  }
}
