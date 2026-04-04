import gradle.kotlin.dsl.accessors._ab250ab1e992ee77c407531f712dd0cd.kotlin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("com.android.kotlin.multiplatform.library")
  id("org.jetbrains.kotlin.plugin.parcelize")
}
kotlin {
  android {

  }

  targets.configureEach {
    val isAndroidTarget = platformType == KotlinPlatformType.androidJvm
    compilations.configureEach {
      compileTaskProvider.configure {
        compilerOptions {
          freeCompilerArgs.addAll(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xexpect-actual-classes", // used for Parcelize in tests
          )
          if (isAndroidTarget) {
            freeCompilerArgs.addAll(
              "-P",
              "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=dev.dwak.lender.lender_app.Parcelize",
            )
          }
        }
      }
    }
  }
}
