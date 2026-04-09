plugins {
  // this is necessary to avoid the plugins to be loaded multiple times
  // in each subproject's classloader
  alias(libs.plugins.androidApplication) apply false
  alias(libs.plugins.androidLibrary) apply false
  alias(libs.plugins.composeMultiplatform) apply false
  alias(libs.plugins.composeCompiler) apply false
  alias(libs.plugins.kotlinJvm) apply false
  alias(libs.plugins.kotlinMultiplatform) apply false
  alias(libs.plugins.kotlinParcelize) apply false
  alias(libs.plugins.kotlinxSerialization) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.ktor) apply false
  alias(libs.plugins.ktorfit) apply false
  alias(libs.plugins.metro) apply false
  alias(libs.plugins.sqldelight) apply false
}

subprojects {
  // Set unique group based on project path
  val pathSegments = project.path.split(":").drop(1)
  if (pathSegments.size > 1) {
    group = "dev.dwak.lender.${pathSegments.joinToString(".")}"
  } else {
    group = "dev.dwak.lender"
  }

  tasks.withType<Jar> {
    val parentName = if (project.parent?.name != rootProject.name) {
      "${project.parent?.name}-"
    } else {
      ""
    }
    archiveBaseName.set("$parentName${project.name}")
  }
}
