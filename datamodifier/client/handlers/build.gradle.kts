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

    commonMain.dependencies {
      implementation(projects.shared)

      api(projects.datamodifier.common.handlers)
      api(projects.datamodifier.client.modifications)

      implementation(projects.models.client)
      implementation(projects.client.network)
      implementation(projects.datastore.client.models)
    }
  }
}
