package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.models.client.ClientLend

data class AdvanceLendMod(
  val lendId: ClientLend.Id,
) : DataModification<AdvanceLendMod.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result
    data object Error : Result
  }
}
