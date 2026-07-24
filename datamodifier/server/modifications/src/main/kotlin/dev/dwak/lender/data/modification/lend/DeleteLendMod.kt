package dev.dwak.lender.data.modification.lend

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerLendId
import dev.dwak.lender.models.server.ServerProfileId

data class DeleteLendMod(
  val lendId: ServerLendId,
  val actorProfileId: ServerProfileId,
) : DataModification<DeleteLendMod.Result> {
  sealed interface Result : DataModification.Result {
    data object NotFound : Result
    data object Unauthorized : Result
    data object Deleted : Result
    data object Denied : Result
    data object Returned : Result
  }
}
