package dev.dwak.lender.repos.client

import dev.dwak.lender.lender_app.getWebApiKey
import dev.dwak.lender.lender_app.getWebServerOrigin
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class WebServerConfigRepo : ServerConfigRepo {
  override val state: StateFlow<ServerConfigState> = MutableStateFlow<ServerConfigState>(
    ServerConfigState.Configured(
      serverUrl = getWebServerOrigin(),
      apiKey = getWebApiKey(),
    )
  ).asStateFlow()
}
