plugins {
  id("client.kmp.android.compose.library")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(libs.androidx.lifecycle.viewmodelCompose)
      implementation(libs.androidx.lifecycle.runtimeCompose)

      api(libs.navigation3.ui)
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
    webMain.dependencies {
      api(libs.navigation3.browser)
    }
  }
}