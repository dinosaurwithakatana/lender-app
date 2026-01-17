package dev.dwak.lender.data.modification.lend

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerLendStatus
import dev.dwak.lender.models.server.ServerProfileId

data class CreateLendMod(
  val itemId: ServerItemId,
  val groupId: ServerGroupId,
  val fromProfileId: ServerProfileId,
  val toProfileId: ServerProfileId,
  val status: ServerLendStatus,
  val quantity: Int,
) : DataModification<CreateLendMod.Result> {
  sealed interface Result : DataModification.Result {
    data object Success: Result

  }
}