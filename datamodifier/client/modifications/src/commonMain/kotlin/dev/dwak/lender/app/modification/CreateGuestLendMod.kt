package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.models.client.ClientItem
import dev.dwak.models.client.ClientLendStatus

data class CreateGuestLendMod(
  val itemId: ClientItem.Id,
  val firstName: String,
  val lastName: String,
  val quantity: Int,
  val lendStatus: ClientLendStatus,
) : DataModification<CreateGuestLendMod.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result
    data object Error : Result
  }
}
