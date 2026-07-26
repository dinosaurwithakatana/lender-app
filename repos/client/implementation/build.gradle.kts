plugins {
  id("kmp.android.library")
}

kotlin {
  applyDefaultHierarchyTemplate()
  sourceSets {
    val nonWebMain by creating {
      dependsOn(commonMain.get())
    }
    androidMain { dependsOn(nonWebMain) }
    jvmMain { dependsOn(nonWebMain) }
    nativeMain.get().dependsOn(nonWebMain)

    commonMain {
      dependencies {
        api(projects.repos.client.api)
        implementation(projects.datastore.client.models)
        implementation(projects.client.network)
      }
    }
    nonWebMain.dependencies {
      implementation(projects.datastore.client.models)
    }
  }
}
