plugins {
  id("client.kmp.android.compose.library")
}

kotlin {

  sourceSets {
    commonMain.dependencies {
      implementation(libs.androidx.lifecycle.viewmodelCompose)
      implementation(libs.androidx.lifecycle.runtimeCompose)
      implementation(libs.androidx.savedstate)
      implementation(libs.circuit.foundation)
      implementation(libs.circuit.gestures)
      implementation(libs.circuit.navigation)
      implementation(libs.circuit.codegen.annotations)

      implementation(projects.client.navigation.integration)

      implementation(projects.client.feature.auth.ui)
      implementation(projects.client.feature.home.ui)

      implementation(projects.repos.client.implementation)
      implementation(projects.datastore.client.serializers)
      implementation(projects.datamodifier.implementation)
      implementation(projects.datamodifier.client.handlers)
      implementation(projects.client.network)

    }
  }
}
