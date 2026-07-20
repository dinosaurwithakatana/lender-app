import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()
plugins {
  id("org.jetbrains.kotlin.jvm")
  id("dev.zacsweers.metro")
}

kotlin {
  jvmToolchain {
    languageVersion.set(
      JavaLanguageVersion.of(libs.versions.java.map { it.toInt() }.get())
    )
  }

}
