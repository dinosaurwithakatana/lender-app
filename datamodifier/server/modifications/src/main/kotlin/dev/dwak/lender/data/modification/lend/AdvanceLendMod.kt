package dev.dwak.lender.data.modification.lend

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerLendId
import dev.dwak.lender.models.server.ServerProfileId

data class AdvanceLendMod(
  val lendId: ServerLendId,
  val actorProfileId: ServerProfileId,
) : DataModification<AdvanceLendMod.Result> {
  sealed interface Result : DataModification.Result {
    data object NotFound : Result
    data object Unauthorized : Result
    data object InvalidTransition : Result
    data object Approved : Result
    data object MarkedLent : Result
  }
}
