package dev.dwak.lender.lender_app

import androidx.datastore.core.DataStore
import dev.dwak.lender.app.network.AuthPluginConfig
import dev.dwak.lender.datastore.DsUserInfo
import dev.dwak.lender.datastore.UserState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.first

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class RealAuthPluginConfig(
  private val dataStore: DataStore<DsUserInfo>
) : AuthPluginConfig {
  override suspend fun token(): String? =
    (dataStore.data.first().userState as? UserState.LoggedIn)?.token
}