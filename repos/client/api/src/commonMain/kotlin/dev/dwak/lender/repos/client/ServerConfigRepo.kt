package dev.dwak.lender.repos.client

import kotlinx.coroutines.flow.StateFlow

sealed interface ServerConfigState {
  data object Loading : ServerConfigState
  data object Unconfigured : ServerConfigState
  data class Configured(val serverUrl: String, val apiKey: String) : ServerConfigState
}

interface ServerConfigRepo {
  val state: StateFlow<ServerConfigState>
}
