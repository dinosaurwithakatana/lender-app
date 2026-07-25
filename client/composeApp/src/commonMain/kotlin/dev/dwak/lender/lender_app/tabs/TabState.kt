package dev.dwak.lender.lender_app.tabs

import com.slack.circuit.runtime.CircuitUiState

data class TabState(
  val selectedBottomBarTabs: BottomBarTabs,
  val dispatch: (TabEvents) -> Unit,
): CircuitUiState
