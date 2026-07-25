package dev.dwak.lender.profile.presenter.home

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface ProfileHomeEvents : CircuitUiEvent {
  data object Refresh : ProfileHomeEvents
}
