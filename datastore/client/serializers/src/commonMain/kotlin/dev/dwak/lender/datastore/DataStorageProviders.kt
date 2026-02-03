package dev.dwak.lender.datastore.dev.dwak.lender.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Storage
import dev.dwak.lender.datastore.DsUserInfo
import dev.dwak.lender.lender_app.AppDir
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.io.files.Path

@ContributesTo(AppScope::class)
interface DataStorageProviders {
  @Provides
  @SingleIn(AppScope::class)
  fun userInfoStorage(@AppDir appDir: Path): Storage<DsUserInfo> = createUserInfoStorage(appDir)

  @Provides
  @SingleIn(AppScope::class)
  fun userInfoDataStore(storage: Storage<DsUserInfo>): DataStore<DsUserInfo> = DataStoreFactory.create(
    storage = storage
  )
}

expect fun createUserInfoStorage(appDir: Path): Storage<DsUserInfo>