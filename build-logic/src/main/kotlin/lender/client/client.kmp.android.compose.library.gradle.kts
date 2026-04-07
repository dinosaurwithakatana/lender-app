import com.android.build.api.variant.impl.capitalizeFirstChar
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompile
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("com.android.kotlin.multiplatform.library")
  id("org.jetbrains.compose")
  id("org.jetbrains.kotlin.plugin.compose")
  id("parcelize")
  id("dev.zacsweers.metro")
  id("org.jetbrains.kotlin.plugin.serialization")
  id("com.google.devtools.ksp")
}

val libs = the<LibrariesForLibs>()

val pathSegments = project.path.split(":").drop(1)
// Set a convention that will be used if not overridden

kotlin {
  compilerOptions {
    freeCompilerArgs.set(listOf("-Xcontext-parameters"))
  }
  @Suppress("UnstableApiUsage")
  android {
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
      implementation(libs.compose.ui.tooling.preview)
      implementation(libs.androidx.activity.compose)
      implementation(libs.androidx.core.ktx)

      implementation(libs.metro.android)
    }
    commonMain {
      kotlin {
        kotlin {
          srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
      }

      dependencies {
        implementation(libs.compose.runtime)
        implementation(libs.compose.foundation)
        implementation(libs.material3)
        implementation(libs.compose.ui)
        implementation(libs.compose.components.resources)
        implementation(libs.compose.ui.tooling.preview)
        implementation(project(":shared"))

        implementation(libs.circuit.runtime)
        implementation(libs.circuit.codegen.annotations)
      }
    }

    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
  }
}

// build.gradle.kts
dependencies {
  "androidRuntimeClasspath"(libs.compose.ui.tooling.preview)
  add("kspCommonMainMetadata", libs.circuit.codegen)
}

ksp { arg("circuit.codegen.mode", "metro") }

tasks.withType(AbstractKotlinCompile::class.java).configureEach {
  incremental = false
  if (this is Kotlin2JsCompile) {
    @Suppress("INVISIBLE_REFERENCE")
    incrementalJsKlib = false
  }
  if (name != "kspCommonMainKotlinMetadata") {
    dependsOn("kspCommonMainKotlinMetadata")
  }
}

tasks.matching { it.name.startsWith("ksp") && it.name != "kspCommonMainKotlinMetadata" }.configureEach {
  dependsOn("kspCommonMainKotlinMetadata")
}