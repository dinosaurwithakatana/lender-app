package dev.dwak.lender.feature.auth.presenter.connect

import androidx.compose.foundation.text.input.TextFieldState
import com.slack.circuit.runtime.CircuitUiState

data class ConnectServerState(
  val serverUrl: TextFieldState,
  val apiKey: TextFieldState,
  val errorMessage: String?,
  val isSaving: Boolean,
  val dispatch: (ConnectServerEvents) -> Unit,
) : CircuitUiState
