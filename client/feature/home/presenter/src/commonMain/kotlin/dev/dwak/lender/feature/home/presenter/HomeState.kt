package dev.dwak.lender.feature.home.presenter

import com.slack.circuit.runtime.CircuitUiState
import dev.dwak.lender.lender_app.Loadable
import dev.dwak.lender.lender_app.Refreshable
import dev.dwak.models.client.ClientItem

data class HomeState(
  val items: List<ClientItem>,
  val dispatch: (HomeEvents) -> Unit,
  override val loading: Boolean,
  override val refreshing: Boolean
) : CircuitUiState, Loadable, Refreshable
