package dev.dwak.lender.lender_app

import androidx.datastore.core.DataStore
import dev.dwak.lender.app.network.AuthPluginConfig
import dev.dwak.lender.datastore.DsUserInfo
import dev.dwak.lender.datastore.UserState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@ContributesBinding(AppScope::class)
class RealAuthPluginConfig(
  private val dataStore: DataStore<DsUserInfo>
) : AuthPluginConfig {
  override suspend fun token(): String? = dataStore.data
    .map { it.userState }
    .filterIsInstance<UserState.LoggedIn>()
    .map { it.token }
    .firstOrNull()
}