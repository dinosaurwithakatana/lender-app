package dev.dwak.lender.feature.auth.presenter.signup

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface SignUpEvents : CircuitUiEvent {
  data object SignUp : SignUpEvents
}