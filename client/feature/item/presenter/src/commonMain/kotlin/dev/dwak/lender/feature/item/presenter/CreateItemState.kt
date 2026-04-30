package dev.dwak.lender.feature.item.presenter

import androidx.compose.foundation.text.input.TextFieldState
import com.slack.circuit.runtime.CircuitUiState

data class CreateItemState(
  val name: TextFieldState,
  val description: TextFieldState,
  val quantity: TextFieldState,
  val dispatch: (CreateItemEvents) -> Unit
): CircuitUiState
