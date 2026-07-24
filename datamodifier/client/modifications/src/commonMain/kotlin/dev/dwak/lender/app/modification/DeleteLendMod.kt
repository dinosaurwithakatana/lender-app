package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.models.client.ClientLend

data class DeleteLendMod(
  val lendId: ClientLend.Id,
) : DataModification<DeleteLendMod.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result
    data object Error : Result
  }
}
