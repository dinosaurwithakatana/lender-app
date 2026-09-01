plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.ktorfit)
  alias(libs.plugins.metro)
}

android {
  namespace = "dev.dwak.lender.android"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  defaultConfig {
    applicationId = "dev.dwak.lender.android"
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()
    versionCode = (System.getenv("VERSION_CODE") ?: "99999").toInt()
    versionName = "1.0"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  signingConfigs {
    getByName("debug") {
      storeFile = rootProject.file("keystore/debug.jks")
      storePassword = "android"
      keyAlias = "key0"
      keyPassword = "android"
    }
    create("release") {
      val releaseKeystore = rootProject.file("keystore/release.jks")
      val storePw = System.getenv("RELEASE_KEYSTORE_PASSWORD")
      val keyPw = System.getenv("RELEASE_KEY_PASSWORD")
      if (releaseKeystore.exists() && storePw != null && keyPw != null) {
        storeFile = releaseKeystore
        storePassword = storePw
        keyAlias = "release"
        keyPassword = keyPw
      }
    }
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      val releaseSigning = signingConfigs.getByName("release")
      if (releaseSigning.storeFile != null) {
        signingConfig = releaseSigning
      }
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
}

dependencies {
  implementation(libs.androidx.appcompat)
  implementation(projects.client.composeApp)
  implementation(projects.shared)
}