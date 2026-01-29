package dev.dwak.lender.feature.auth.presenter.launch

import com.slack.circuit.runtime.CircuitUiState

data class LaunchState(
  val dispatch: (LaunchEvents) -> Unit
): CircuitUiState
