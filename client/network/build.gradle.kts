plugins {
  id("kmp.jvm.library")
  alias(libs.plugins.ksp)
  alias(libs.plugins.ktorfit)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(libs.ktorfit)
      implementation(libs.ktor.clientContentNegotiation)
      implementation(libs.ktor.serialzationJson)
      api(libs.ktorfit.response)
      api(projects.models.api)
    }
  }
}

ktorfit {
  compilerPluginVersion.set("2.3.3")
}
