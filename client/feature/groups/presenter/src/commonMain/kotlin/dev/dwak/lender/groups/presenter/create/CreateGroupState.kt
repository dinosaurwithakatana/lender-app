package dev.dwak.lender.groups.presenter.create

import androidx.compose.foundation.text.input.TextFieldState
import com.slack.circuit.runtime.CircuitUiState

data class CreateGroupState(
  val name: TextFieldState,
  val dispatch: (CreateGroupEvents) -> Unit
) : CircuitUiState