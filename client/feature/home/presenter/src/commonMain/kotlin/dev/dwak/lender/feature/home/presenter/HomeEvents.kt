package dev.dwak.lender.feature.home.presenter

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface HomeEvents: CircuitUiEvent {
  data object Logout : HomeEvents
}