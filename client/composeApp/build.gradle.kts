import com.android.build.api.dsl.androidLibrary
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  id("client.kmp.android.compose.library")
}

kotlin {

  sourceSets {
    commonMain.dependencies {
      implementation(libs.androidx.lifecycle.viewmodelCompose)
      implementation(libs.androidx.lifecycle.runtimeCompose)

      implementation(projects.client.navigation.core)

      implementation(projects.client.feature.auth.ui)

    }
  }
}
