plugins {
  `kotlin-dsl`
}
val javaVersion = JavaLanguageVersion.of(libs.versions.java.map { it.toInt() }.get())

dependencies {
  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

  implementation(libs.bundles.build.plugins)
}
