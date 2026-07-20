package dev.dwak.lender.feature.home.presenter

import com.slack.circuit.runtime.CircuitUiState
import dev.dwak.models.client.ClientItem

data class HomeState(
  val items: List<ClientItem>,
  val dispatch: (HomeEvents) -> Unit
) : CircuitUiState
