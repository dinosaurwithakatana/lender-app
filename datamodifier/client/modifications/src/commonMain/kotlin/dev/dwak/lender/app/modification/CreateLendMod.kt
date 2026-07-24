package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.models.client.ClientGroup
import dev.dwak.models.client.ClientItem
import dev.dwak.models.client.ClientProfile

data class CreateLendMod(
  val itemId: ClientItem.Id,
  val groupId: ClientGroup.Id,
  val toProfileId: ClientProfile.Id,
  val quantity: Int,
) : DataModification<CreateLendMod.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result
    data object Error : Result
  }
}
