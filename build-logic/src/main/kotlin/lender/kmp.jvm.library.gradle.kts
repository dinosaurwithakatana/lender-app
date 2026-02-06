import com.android.build.api.variant.impl.capitalizeFirstChar
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompile
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import kotlin.jvm.java

plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("dev.zacsweers.metro")
}

val libs = the<LibrariesForLibs>()

val pathSegments = project.path.split(":").drop(1)
// Set a convention that will be used if not overridden

kotlin {
  compilerOptions {
    freeCompilerArgs.set(listOf("-Xcontext-parameters"))
  }
  jvm()

  listOf(
    iosArm64(),
    iosSimulatorArm64()
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = pathSegments.joinToString { it.capitalizeFirstChar() }
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
      implementation(project(":shared"))
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
  }
}

tasks.withType(AbstractKotlinCompile::class.java).configureEach {
  incremental = false
  if (this is Kotlin2JsCompile) {
    @Suppress("INVISIBLE_REFERENCE")
    incrementalJsKlib = false
  }
}
