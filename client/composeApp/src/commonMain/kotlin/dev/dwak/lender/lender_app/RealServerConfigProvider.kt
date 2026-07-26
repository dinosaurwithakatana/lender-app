package dev.dwak.lender.lender_app

import dev.dwak.lender.app.network.ServerConfig
import dev.dwak.lender.app.network.ServerConfigProvider
import dev.dwak.lender.repos.client.ServerConfigRepo
import dev.dwak.lender.repos.client.ServerConfigState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class RealServerConfigProvider(
  private val repo: ServerConfigRepo,
) : ServerConfigProvider {
  override suspend fun current(): ServerConfig {
    val state = repo.state.value
    check(state is ServerConfigState.Configured) {
      "ServerConfig requested before server was configured (state=$state)"
    }
    return ServerConfig(serverUrl = state.serverUrl, apiKey = state.apiKey)
  }
}
