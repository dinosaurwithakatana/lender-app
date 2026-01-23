plugins {
  id("server.library")
  alias(libs.plugins.sqldelight)
}

sqldelight {
  databases {
    create("Database") {
      packageName.set("dev.dwak.lender.db")
      schemaOutputDirectory.set(file("src/main/sqldelight/databases"))
    }
  }
}

dependencies {
  implementation(libs.sqldelight.coroutines)
  implementation(libs.sqldelight.driver)
  implementation(projects.shared)
}