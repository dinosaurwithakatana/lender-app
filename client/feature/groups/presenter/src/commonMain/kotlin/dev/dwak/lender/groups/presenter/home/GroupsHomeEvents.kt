package dev.dwak.lender.groups.presenter.home

import com.slack.circuit.runtime.CircuitUiEvent
import dev.dwak.models.client.ClientGroup

sealed interface GroupsHomeEvents : CircuitUiEvent {
  data object CreateGroup : GroupsHomeEvents
  data class OpenGroup(val groupId: ClientGroup.Id) : GroupsHomeEvents
}