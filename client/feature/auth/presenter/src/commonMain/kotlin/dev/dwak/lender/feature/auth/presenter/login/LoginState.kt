package dev.dwak.lender.feature.auth.presenter.login

import androidx.compose.foundation.text.input.TextFieldState
import com.slack.circuit.runtime.CircuitUiState

data class LoginState(
  val username: TextFieldState = TextFieldState(),
  val password: TextFieldState = TextFieldState(),
  val dispatch: (LoginEvents) -> Unit
): CircuitUiState
