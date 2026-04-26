package dev.dwak.lender.data.modification.lend

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerProfile
import dev.dwak.lender.models.server.ServerProfileId

data class CreateGuestLendMod(
  val itemId: ServerItemId,
  val fromProfileId: ServerProfileId,
  val firstName: String,
  val lastName: String,
  val quantity: Int,
) : DataModification<CreateGuestLendMod.Result> {
  sealed interface Result : DataModification.Result {
    data class Success(val guestProfile: ServerProfile) : Result
    data object InsufficientQuantity : Result
  }
}
