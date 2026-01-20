package dev.dwak.lender.data.modification.group

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerGroupMembershipStatus
import dev.dwak.lender.models.server.ServerProfileId

data class AddProfileToGroupMod(
  val profileId: ServerProfileId,
  val groupId: ServerGroupId,
  val status: ServerGroupMembershipStatus
) : DataModification<AddProfileToGroupMod.Result> {
  sealed interface Result : DataModification.Result {
    data object Success: Result
  }
}