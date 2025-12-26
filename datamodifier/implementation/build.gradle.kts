import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.metro)
}

kotlin {
  iosArm64()
  iosSimulatorArm64()

  jvm()

  js {
    browser()
  }

  @OptIn(ExperimentalWasmDsl::class)
  wasmJs {
    browser()
  }

  sourceSets {
    commonMain.dependencies {
      // put your Multiplatform dependencies here
      api(projects.datamodifier.api)
      implementation(projects.shared)
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
  }
}
