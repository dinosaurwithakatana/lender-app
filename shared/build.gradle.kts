import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompile
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import kotlin.jvm.java

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.metro)
  alias(libs.plugins.androidLibrary)
  alias(libs.plugins.kotlinParcelize)
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
      api(libs.kotlinx.io)
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
  }
}

afterEvaluate {
  tasks.withType(AbstractKotlinCompile::class.java).configureEach {
    incremental = false
    if (this is Kotlin2JsCompile) {
      @Suppress("INVISIBLE_REFERENCE")
      incrementalJsKlib = false
    }
  }
}
