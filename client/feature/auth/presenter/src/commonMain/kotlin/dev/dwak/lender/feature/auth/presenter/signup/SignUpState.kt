package dev.dwak.lender.feature.auth.presenter.signup

import androidx.compose.foundation.text.input.TextFieldState
import com.slack.circuit.runtime.CircuitUiState

data class SignUpState(
  val username: TextFieldState = TextFieldState(),
  val password: TextFieldState = TextFieldState(),
  val confirmPassword: TextFieldState = TextFieldState(),
  val inviteCode: TextFieldState = TextFieldState(),
  val dispatch: (SignUpEvents) -> Unit
): CircuitUiState
