package dev.dwak.lender.feature.auth.presenter.login

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface LoginEvents: CircuitUiEvent {
  data object Login: LoginEvents
}