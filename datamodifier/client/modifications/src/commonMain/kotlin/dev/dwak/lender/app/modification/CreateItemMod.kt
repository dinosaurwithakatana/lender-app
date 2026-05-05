package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.models.client.ClientItem

data class CreateItemMod(
  val name: String,
  val description: String?,
  val quantity: Int
) : DataModification<CreateItemMod.Result> {
  sealed interface Result : DataModification.Result {
    data class Success(
      val id: ClientItem.Id
    ) : Result

    data object Error: Result
  }
}
