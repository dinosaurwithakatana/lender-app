package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.models.client.ClientGroup

data class CreateGroupMod(
  val name: String,
) : DataModification<CreateGroupMod.Result> {
  sealed interface Result : DataModification.Result {
    data class Success(
      val id: ClientGroup.Id
    ) : Result

    data object Error : Result
  }
}
