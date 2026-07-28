plugins {
  id("client.kmp.android.compose.library")
}

kotlin {

  sourceSets {
    commonMain.dependencies {
      implementation(libs.androidx.lifecycle.viewmodelCompose)
      implementation(libs.androidx.lifecycle.runtimeCompose)
      implementation(libs.androidx.savedstate)
      implementation(libs.circuit.effects)
      implementation(libs.circuit.foundation)
      implementation(libs.circuit.gestures)
      implementation(libs.circuit.navigation)
      implementation(libs.circuit.runtime.navigation)
      implementation(libs.circuit.codegen.annotations)
      implementation(libs.circuit.serialization)
      implementation(libs.calf.ui)

      implementation(projects.client.navigation.core)

      implementation(projects.client.feature.auth.ui)
      implementation(projects.client.feature.home.ui)
      implementation(projects.client.feature.item.ui)
      implementation(projects.client.feature.groups.ui)
      implementation(projects.client.feature.lend.ui)
      implementation(projects.client.feature.profile.ui)

      implementation(projects.repos.client.implementation)
      implementation(projects.datastore.client.serializers)
      implementation(projects.datamodifier.implementation)
      implementation(projects.datamodifier.client.handlers)
      api(projects.client.network)
    }

    wasmJsMain.dependencies {
      implementation(libs.kotlinx.browser)
    }
    webMain.dependencies {
    }
  }
}
