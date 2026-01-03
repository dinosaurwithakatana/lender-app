import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.metro)
  alias(libs.plugins.androidLibrary)
}

kotlin {
  androidLibrary {
    namespace = "dev.dwak.lender.lender_app.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    minSdk = libs.versions.android.minSdk.get().toInt()
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_17)
    }
  }

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
      api(libs.kotlinx.coroutines.core)
      api(libs.napier)
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
  }
}
