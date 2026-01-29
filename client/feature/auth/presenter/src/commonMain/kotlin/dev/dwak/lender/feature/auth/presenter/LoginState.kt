package dev.dwak.lender.feature.auth.presenter

import com.slack.circuit.runtime.CircuitUiState

data class LoginState(
  val eventSink: (LoginEvents) -> Unit
): CircuitUiState
