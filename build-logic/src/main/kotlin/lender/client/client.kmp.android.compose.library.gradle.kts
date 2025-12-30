import com.android.build.api.dsl.androidLibrary
import com.android.build.api.variant.impl.capitalizeFirstChar
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("com.android.kotlin.multiplatform.library")
  id("org.jetbrains.compose")
  id("org.jetbrains.kotlin.plugin.compose")
  id("dev.zacsweers.metro")
  id("org.jetbrains.kotlin.plugin.serialization")
}

val libs = the<LibrariesForLibs>()

val pathSegments = project.path.split(":").drop(1)
// Set a convention that will be used if not overridden

kotlin {
  compilerOptions {
    freeCompilerArgs.set(listOf("-Xcontext-parameters"))
  }
  @Suppress("UnstableApiUsage")
  androidLibrary {
    namespace = pathSegments.joinToString("-")
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    minSdk = libs.versions.android.minSdk.get().toInt()
    compilerOptions {
      jvmTarget.set(JvmTarget.fromTarget(libs.versions.java.get()))
    }
  }

  listOf(
    iosArm64(),
    iosSimulatorArm64()
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = pathSegments.joinToString(separator = "") { it.capitalizeFirstChar() }
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
    androidMain.dependencies {
      implementation(compose.preview)
      implementation(libs.androidx.activity.compose)
      implementation(libs.androidx.core.ktx)

      implementation(libs.metro.android)
    }
    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(project(":shared"))
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
  }
}

// build.gradle.kts
dependencies {
  "androidRuntimeClasspath"(compose.uiTooling)
}

metro {
  generateContributionHintsInFir.set(true)
}
