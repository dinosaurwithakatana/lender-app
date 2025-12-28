import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("dev.zacsweers.metro")
}

metro {
  contributesAsInject.set(true)
}

kotlin {
  compilerOptions {
    freeCompilerArgs.set(listOf("-Xcontext-parameters"))
  }
}
