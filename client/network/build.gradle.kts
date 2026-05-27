plugins {
  id("kmp.android.library")
  alias(libs.plugins.ksp)
  alias(libs.plugins.ktorfit)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(libs.ktorfit)
      implementation(libs.ktor.clientContentNegotiation)
      implementation(libs.ktor.clientLogging)
      implementation(libs.ktor.serialzationJson)
      api(libs.ktorfit.response)
      api(projects.models.api)
    }
  }
}

ktorfit {
  compilerPluginVersion.set("2.3.3")
}
