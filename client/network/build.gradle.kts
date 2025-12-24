import com.android.build.api.dsl.androidLibrary
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.androidLibrary)
  alias(libs.plugins.ksp)
  alias(libs.plugins.ktorfit)
  alias(libs.plugins.metro)
}

kotlin {
  @Suppress("UnstableApiUsage")
  androidLibrary {
    namespace = "dev.dwak.lender.app.network"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    minSdk = libs.versions.android.minSdk.get().toInt()
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_17)
    }
  }

  listOf(
    iosArm64(),
    iosSimulatorArm64()
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = "network"
      isStatic = true
    }
  }

  js {
    browser()
    binaries.executable()
  }

  @OptIn(ExperimentalWasmDsl::class)
  wasmJs {
    browser()
    binaries.executable()
  }

  sourceSets {
    commonMain.dependencies {
      implementation(projects.shared)

      implementation(libs.ktorfit)
      api(projects.models.api)
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
  }
}

ktorfit {
  compilerPluginVersion.set("2.3.3")
}
