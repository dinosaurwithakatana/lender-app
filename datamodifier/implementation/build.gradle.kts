plugins {
  id("kmp.jvm.library")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      // put your Multiplatform dependencies here
      api(projects.datamodifier.api)
      implementation(projects.shared)
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
  }
}
