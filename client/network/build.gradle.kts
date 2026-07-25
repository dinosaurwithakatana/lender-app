plugins {
  id("kmp.android.library")
  alias(libs.plugins.ksp)
  alias(libs.plugins.ktorfit)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(libs.ktorfit)
      implementation(libs.ktor.clientCore)
      implementation(libs.ktor.clientContentNegotiation)
      implementation(libs.ktor.clientLogging)
      implementation(libs.ktor.serialzationJson)
      api(libs.ktorfit.response)
      api(projects.models.api)
    }
    androidMain.dependencies {
      implementation(libs.ktor.clientOkhttp)
    }
    jvmMain.dependencies {
      implementation(libs.ktor.clientCio)
    }
    iosMain.dependencies {
      implementation(libs.ktor.clientDarwin)
    }
    jsMain.dependencies {
      implementation(libs.ktor.clientJs)
    }
    wasmJsMain.dependencies {
      implementation(libs.ktor.clientJs)
    }
  }
}
