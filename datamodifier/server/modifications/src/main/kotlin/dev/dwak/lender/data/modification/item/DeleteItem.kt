package dev.dwak.lender.data.modification.item

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerProfileId

data class DeleteItem(
  val id: ServerItemId,
  val ownedBy: ServerProfileId
) : DataModification<DeleteItem.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result
    data object Unauthorized: Result
    data object Failure : Result
  }
}