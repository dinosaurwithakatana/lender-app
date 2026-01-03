package dev.dwak.lender.data.modification.group

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerProfileId

data class DeleteGroup(
  val id: ServerGroupId,
  val requestingProfileId: ServerProfileId,
) : DataModification<DeleteGroup.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result

    data object Unauthorized : Result
  }
}
