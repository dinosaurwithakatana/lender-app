package dev.dwak.lender.lender_app.tabs

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface TabEvents : CircuitUiEvent {
  data class TabSelected(val tab: BottomBarTabs) : TabEvents
}