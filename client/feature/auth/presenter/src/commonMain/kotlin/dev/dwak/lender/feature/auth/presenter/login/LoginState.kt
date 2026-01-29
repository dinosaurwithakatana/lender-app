package dev.dwak.lender.feature.auth.presenter.login

import com.slack.circuit.runtime.CircuitUiState

data class LoginState(
  val dispatch: (LoginEvents) -> Unit
): CircuitUiState
