import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
  id("client.kmp.android.compose.library")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(libs.androidx.lifecycle.viewmodelCompose)
      implementation(libs.androidx.lifecycle.runtimeCompose)
      api(libs.circuit.foundation)

      api(libs.navigation3.ui)

      api(projects.client.navigation.core)
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
    webMain.dependencies {
      api(libs.navigation3.browser)
    }
  }
}
