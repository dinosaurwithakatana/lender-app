package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.models.client.ClientMembership

data class ApproveGroupMembershipMod(
  val groupId: ClientMembership.Id,
): DataModification<ApproveGroupMembershipMod.Result> {
  sealed interface Result: DataModification.Result {
    data object Success : Result
    data object Failure : Result
  }
}
