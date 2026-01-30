package dev.dwak.lender.feature.home.presenter

import com.slack.circuit.runtime.CircuitUiState

data class HomeState(
  val dispatch: (HomeEvents) -> Unit
): CircuitUiState
