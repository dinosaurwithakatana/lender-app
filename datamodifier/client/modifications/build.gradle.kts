import com.android.build.api.dsl.androidLibrary
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  id("kmp.jvm.library")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.shared)

      implementation(projects.datamodifier.api)
      implementation(projects.client.network)
    }
  }
}
