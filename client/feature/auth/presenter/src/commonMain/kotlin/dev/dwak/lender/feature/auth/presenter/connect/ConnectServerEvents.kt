package dev.dwak.lender.feature.auth.presenter.connect

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface ConnectServerEvents : CircuitUiEvent {
  data object Connect : ConnectServerEvents

  data object ToggleApiKeyRevealed : ConnectServerEvents
}
