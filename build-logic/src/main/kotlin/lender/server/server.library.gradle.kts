import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()
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

  jvmToolchain {
    languageVersion.set(
      JavaLanguageVersion.of(libs.versions.java.map { it.toInt() }.get())
    )
  }

}
