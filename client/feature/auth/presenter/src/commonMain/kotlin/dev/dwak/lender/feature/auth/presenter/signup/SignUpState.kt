package dev.dwak.lender.feature.auth.presenter.signup

import com.slack.circuit.runtime.CircuitUiState

data class SignUpState(
  val dispatch: (SignUpEvents) -> Unit
): CircuitUiState
