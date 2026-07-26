package dev.dwak.lender.data.modification.group

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerGroupMembershipId
import dev.dwak.lender.models.server.ServerProfileId

data class RemoveMembershipMod(
  val membershipId: ServerGroupMembershipId,
  val actingProfileId: ServerProfileId,
) : DataModification<RemoveMembershipMod.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result
    data object NotFound : Result
    data object Unauthorized : Result
  }
}
