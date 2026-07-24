package dev.dwak.lender.lend.presenter.create

import com.slack.circuit.runtime.CircuitUiEvent
import dev.dwak.models.client.ClientGroup
import dev.dwak.models.client.ClientItem
import dev.dwak.models.client.ClientProfile

sealed interface CreateLendEvents : CircuitUiEvent {
  data object Back : CreateLendEvents
  data object AttemptSave : CreateLendEvents
  data class SelectItem(val item: ClientItem) : CreateLendEvents
  data class SelectMode(val mode: LendMode) : CreateLendEvents
  data class SelectGroup(val group: ClientGroup) : CreateLendEvents
  data class SelectMember(val member: ClientProfile) : CreateLendEvents
}

enum class LendMode {
  GUEST, GROUP_MEMBER
}
