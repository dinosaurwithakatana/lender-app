package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.models.client.ClientGroup
import dev.dwak.models.client.ClientProfile

data class AddMemberMod(
  val groupId: ClientGroup.Id,
  val profileId: ClientProfile.Id,
) : DataModification<AddMemberMod.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result
    data object Error : Result
  }
}
