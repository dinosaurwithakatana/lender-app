package dev.dwak.lender.feature.home.presenter

import com.slack.circuit.runtime.CircuitUiEvent
import dev.dwak.models.client.ClientItem

sealed interface HomeEvents : CircuitUiEvent {
  data object Refresh : HomeEvents
  data object NavigateToCreateItem : HomeEvents
  data class RequestDeleteItem(val item: ClientItem) : HomeEvents
  data object ConfirmDeleteItem : HomeEvents
  data object CancelDeleteItem : HomeEvents
}
