package dev.dwak.lender.data.modification.item

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerProfileId

data class CreateItemMod(
    val name: String,
    val description: String?,
    val quantity: Int = 1,
    val ownedBy: ServerProfileId,
): DataModification<CreateItemMod.Result> {
  sealed interface Result: DataModification.Result {
    data class Success(val id: ServerItemId): Result
  }
}