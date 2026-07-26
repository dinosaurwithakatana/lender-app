package dev.dwak.lender.repos.client

import androidx.datastore.core.DataStore
import dev.dwak.lender.datastore.DsServerConfig
import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class RealServerConfigRepo(
  dataStore: DataStore<DsServerConfig>,
  @Io scope: CoroutineScope,
) : ServerConfigRepo {
  override val state: StateFlow<ServerConfigState> = dataStore.data
    .map { it.toState() }
    .stateIn(
      scope = scope,
      started = SharingStarted.Eagerly,
      initialValue = ServerConfigState.Loading,
    )

  private fun DsServerConfig.toState(): ServerConfigState {
    val url = serverUrl
    val key = apiKey
    return if (!url.isNullOrBlank() && !key.isNullOrBlank()) {
      ServerConfigState.Configured(serverUrl = url, apiKey = key)
    } else {
      ServerConfigState.Unconfigured
    }
  }
}
