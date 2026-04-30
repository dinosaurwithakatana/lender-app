package dev.dwak.lender.feature.auth.presenter.launch

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface LaunchEvents: CircuitUiEvent {
  data object GoToLogin: LaunchEvents

  data object GoToSignUp: LaunchEvents
}